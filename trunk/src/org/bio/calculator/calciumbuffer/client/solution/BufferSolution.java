package org.bio.calculator.calciumbuffer.client.solution;

import java.util.ArrayList;

import org.bio.calculator.calciumbuffer.client.Calculator;
import org.bio.calculator.calciumbuffer.client.solute.MetalSolute;
import org.bio.calculator.calciumbuffer.client.solute.Solute;
import org.bio.calculator.calciumbuffer.client.species.Species;
import org.bio.calculator.calciumbuffer.client.species.Species.Type;

public class BufferSolution {

    private Double pH;
    private Double H;
    private Double temperature; // in Celsius
    
    private ArrayList<Solute> soluteList;
    private Double ionicStrength;

    public BufferSolution(Double newpH, Double newIonicStrength, Double newTemperature)
    {
        pH = newpH;
        temperature = newTemperature;
        ionicStrength = newIonicStrength;
        H = CalculateH(pH, temperature, ionicStrength);
    }
    
    public boolean Iterate(Double convergenceCriterion, int iterationsLimit)
    {
    	boolean convergence = false;
    	boolean stop = false;
    	int iteration = 0;
    	Double ionicStrengthGap;
    	Species.Type type;
    	
    	while (!stop)
    	{
        	iteration++;
        	Double newIonicStrength = 0.0;
        	Double delta = 0.0;
        	
            for (Solute solute : soluteList)
            {
            	newIonicStrength += solute.getISC();
            	type = solute.getSpecies().getType();
            	if (type == Type.ligand || type == Type.metal)
            	{
            		delta += Math.abs( solute.update() );	
            	}
            }
            convergence = (delta <= convergenceCriterion);
            stop = (convergence || (iteration > iterationsLimit));
            
            ionicStrengthGap = this.ionicStrength - newIonicStrength;
            
            soluteList.add(new MetalSolute(this, Calculator.K , ionicStrengthGap/2, Solute.State.total ));
            soluteList.add(new Solute(this, Calculator.Cl , ionicStrengthGap/2, Solute.State.total ));
    	}
    	
    	return convergence;
    }

    public Double CalculateH(Double pH, Double temperature, Double ionicStrength)
    {
        return Math.pow(10,-pH)/ gammaH(temperature, ionicStrength);
    }

    private Double gammaH(Double temperature, Double ionicStrength) // per Bers et al. eqn. 15:
    {
        Double B = 0.522932 * Math.exp(0.0327016 * temperature) + 4.015942;
        return 0.145045 * Math.exp(-B * ionicStrength) + 0.063546 * Math.exp(-43.97704 * ionicStrength) + 0.695634;
    }
    
    public Double getpH ()
    {
        return pH; 
    }
    
    public Double getH ()
    {
        return H; 
    }
    
    public ArrayList<Solute> getSoluteList()
    {
        return soluteList; 
    }
    
    public void add(Solute newSolute)
    {
    	int index;
    	Solute solute;
    	
    	if (soluteList.contains(newSolute))
    	{
    		index = soluteList.indexOf(newSolute);
    		solute = soluteList.get(index);
    		solute.setTotalConcentration(solute.getTotalConcentration() + newSolute.getTotalConcentration());
    		soluteList.set(index, solute);
    	}
    	else
    	{
    		soluteList.add(newSolute);
    	}
    }
}
