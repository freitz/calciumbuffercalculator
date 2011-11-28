package org.bio.calculator.calciumbuffer.client.solute;

import org.bio.calculator.calciumbuffer.client.solution.BufferSolution;
import org.bio.calculator.calciumbuffer.client.species.BufferingAgent;

public class BufferSolute extends Solute
{
    private BufferingAgent bufferingAgent;

    public BufferSolute(BufferSolution newBufferSolution, BufferingAgent bufferingAgent, Double concentration)
    {
        super(newBufferSolution, bufferingAgent, concentration, State.total);
    	ISC = CalculateISC();
    }

    private Double CalculateISC()
    {
        Double f = Math.pow(10, bufferSolution.getpH() - bufferingAgent.getpKa());
        Double ISC = totalConcentration * (1 / (1 + f)) / 2;
        if (bufferingAgent.isAnionic()) ISC *= f;
        return ISC;
    }
}
