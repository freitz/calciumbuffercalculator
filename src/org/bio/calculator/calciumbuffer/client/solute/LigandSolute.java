package org.bio.calculator.calciumbuffer.client.solute;

import java.util.Map;
import org.bio.calculator.calciumbuffer.client.ion.Ligand;
import org.bio.calculator.calciumbuffer.client.ion.Metal;
import org.bio.calculator.calciumbuffer.client.solute.ComplexSolute;
import org.bio.calculator.calciumbuffer.client.solution.BufferSolution;

public class LigandSolute extends IonSolute
{
    protected Ligand ligand;
    private BufferSolution bufferSolution;
    Double SUM; //i.e., apparent-free-conc to true-free-conc ratio
    private Map<Metal, ComplexSolute> complexes = null;

    public LigandSolute (BufferSolution bufferSolution, Ligand ligand, Double concentration, State state) {
    	super(bufferSolution,ligand,concentration,state);
        this.ligand = ligand;
        this.bufferSolution = bufferSolution;
        SUM = calculateSUM(5);
        charge = calculateWeightedSUM(4) / SUM; //i.e., mean square charge
    }

    public void addComplex(Metal metal, ComplexSolute complex) 
    {
        complexes.put(metal, complex);
    }
    
    public void Update()
    {
        if (this.state == State.free)
        {
            this.totalConcentration = freeConcentration * calculateSumBoundPerFree();
        }
        else
        {
            this.freeConcentration = totalConcentration / calculateSumBoundPerFree();
        }
        this.ISC = calculateISC();
    }
    
    public Double calculateSumBoundPerFree()
    {
        Double result = 1.0;
        Metal m;
        
        for (MetalSolute metalSolute : this.bufferSolution.getMetalSoluteList())
        {
        	m = metalSolute.getMetal();
            if (m.getColumn() == 2) 
            {
                result += getComplexes().get(m).getKapp() * metalSolute.freeConcentration;
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

	public Map<Metal, ComplexSolute> getComplexes() {
		return complexes;
	}
}