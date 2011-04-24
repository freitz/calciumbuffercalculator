package org.bio.calculator.calciumbuffer.client.solute;

import org.bio.calculator.calciumbuffer.client.ion.Metal;
import org.bio.calculator.calciumbuffer.client.solution.BufferSolution;

public class MetalSolute extends IonSolute
{
    private Metal metal;

    public MetalSolute (BufferSolution bufferSolution, Metal metal, double concentration, State state)
    {
    	super (bufferSolution,metal,concentration,state);
        this.metal = metal;
    }

    public void Update()
    {
        if (this.state == State.free)
        {
            this.totalConcentration = GetTotal();
        }
        else
        {
            this.freeConcentration = GetFree();
        }
        this.ISC = GetISC();
    }

    private double GetISC()
    {
        return 0;///////////////////////////////////////////////////////////////////////////
    }

    private double GetFree()
    {
        return this.totalConcentration / GetSumBoundPerFree();
    }

    private double GetTotal()
    {
        return this.freeConcentration * GetSumBoundPerFree();
    }

    private double GetSumBoundPerFree()
    {
        double result = 1;
        for (LigandSolute ligandSolute : bufferSolution.getLigandSoluteList())
        {
            result += ligandSolute.getComplexSolutes()[metal.getRow()][ metal.getColumn()].getKapp() * ligandSolute.freeConcentration;
        }
        return result;
    }

    public Metal getMetal ()
    {
        return this.metal; 
    }
}
