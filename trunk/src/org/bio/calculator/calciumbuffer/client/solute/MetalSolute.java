package org.bio.calculator.calciumbuffer.client.solute;

import org.bio.calculator.calciumbuffer.client.ion.Metal;
import org.bio.calculator.calciumbuffer.client.solution.BufferSolution;

public class MetalSolute extends IonSolute
{
    private Metal metal;

    public MetalSolute (BufferSolution bufferSolution, Metal metal, Double concentration, State state)
    {
    	super (bufferSolution, metal, concentration, state);
        this.metal = metal;
        charge = (Double) (double) metal.getColumn();
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
    
    private Double calculateSumBoundPerFree()
    {
        Double result = 1.0;
        for (LigandSolute ligandSolute : bufferSolution.getLigandSoluteList())
        {
            result += ligandSolute.getComplexes().get(metal).getKapp() * ligandSolute.freeConcentration;
        }
        return result;
    }
    
    public Metal getMetal()
    {
        return metal; 
    }
}
