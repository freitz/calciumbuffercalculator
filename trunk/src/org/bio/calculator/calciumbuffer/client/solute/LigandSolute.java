package org.bio.calculator.calciumbuffer.client.solute;

import java.util.Map;
import org.bio.calculator.calciumbuffer.client.solute.ComplexSolute;
import org.bio.calculator.calciumbuffer.client.solution.BufferSolution;
import org.bio.calculator.calciumbuffer.client.species.Ligand;
import org.bio.calculator.calciumbuffer.client.species.Metal;
import org.bio.calculator.calciumbuffer.client.species.Species.Type;

public class LigandSolute extends Solute
{
    protected Ligand ligand;
    Double SUM; //i.e., apparent-free-conc to true-free-conc ratio
    private Map<Metal, ComplexSolute> complexes = null;
    protected BufferSolution bufferSolution;

    public LigandSolute (BufferSolution newBufferSolution, Ligand newLigand, Double concentration, State state) {
    	super(newBufferSolution, newLigand, concentration, state);
    	ligand = newLigand;
        SUM = calculateSUM(5);
        charge = calculateWeightedSUM(4) / SUM; //i.e., mean square charge
    }

    public void addComplex(Metal metal, ComplexSolute complex) 
    {
        complexes.put(metal, complex);
    }
    
    public Double update()
    {
    	Double delta;
        if (state == State.free)
        {
            totalConcentration = freeConcentration * calculateSumBoundPerFree();
            delta = totalConcentration - lastConcentration;
        }
        else
        {
            freeConcentration = totalConcentration / calculateSumBoundPerFree();
            delta = freeConcentration - lastConcentration;
        }
        this.ISC = calculateISC();
        return delta;
    }
    
    public Double calculateSumBoundPerFree()
    {
        Double result = 1.0;
        MetalSolute metalSolute;
        Metal m;
        
        for (Solute solute : bufferSolution.getSoluteList())
        {
        	if (solute.species.getType() == Type.metal)
        	{
	        	metalSolute = (MetalSolute) solute;
	        	m = metalSolute.getMetal();
	            if (m.getColumn() == 2) 
	            {
	                result += getComplexes().get(m).getKapp() * metalSolute.freeConcentration;
	            }
        	}
        }
        return result;
    }
    
    protected Double calculateSUM(int numTerms) 
    {
        Double result = 0.0;
        
        for (int counter = 0; counter < numTerms; counter++) 
        {
            result += calculateSUMTerm(counter);
        }

        return result;
    }  
    
    protected Double calculateWeightedSUM(int numTerms) 
    {
        Double result = 0.0;
        
        for (int counter = 0; counter < numTerms; counter++) 
        {
            result += calculateWeightedSUMTerm(counter, ligand.getValence());
        }

        return result;
    }  
    
    protected Double calculateSUMTerm(int N)
    {
    	Double K;
    	
    	if (N==0) { 
    		K = 1.0; 
    	}
    	else { 
    		K = ligand.getKsFor("H")[N-1] * Math.pow(bufferSolution.getH(), N); 
    	}
    	
    	return K;
    }
    
    protected Double calculateWeightedSUMTerm(int N, int valence)
    {
        return calculateSUMTerm(N) * Math.pow(valence - N, 2); 
    }
    
	public Ligand getLigand() {
		return ligand;
	}
	
	public BufferSolution getBufferSolution()
	{
		return bufferSolution;
	}

	public Map<Metal, ComplexSolute> getComplexes() {
		return complexes;
	}
}