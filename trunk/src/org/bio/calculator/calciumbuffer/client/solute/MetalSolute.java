package org.bio.calculator.calciumbuffer.client.solute;

import org.bio.calculator.calciumbuffer.client.solution.BufferSolution;
import org.bio.calculator.calciumbuffer.client.species.Metal;
import org.bio.calculator.calciumbuffer.client.species.Species.Type;

public class MetalSolute extends Solute
{
    private Metal metal;

    public MetalSolute (BufferSolution bufferSolution, Metal metal, Double concentration, State state)
    {
    	super (bufferSolution, metal, concentration, state);
        this.metal = metal;
        charge = (Double) (double) metal.getColumn();
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
    
    private Double calculateSumBoundPerFree()
    {
        Double result = 1.0;
        LigandSolute ligandSolute;
        
        for (Solute solute : bufferSolution.getSoluteList())
        {
        	if (solute.species.getType() == Type.ligand)
        	{
        		ligandSolute = (LigandSolute) solute;
        		result += ligandSolute.getComplexes().get(metal).getKapp() * ligandSolute.freeConcentration;
        	}
        }
        return result;
    }
    
    public Metal getMetal()
    {
        return metal; 
    }
}
