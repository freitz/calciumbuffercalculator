package org.bio.calculator.calciumbuffer.client.solute;

import org.bio.calculator.calciumbuffer.client.buffering_agent.BufferingAgent;
import org.bio.calculator.calciumbuffer.client.solution.BufferSolution;

public class BufferSolute
{
    private BufferSolution bufferSolution;
    private BufferingAgent bufferingAgent;
    private double concentration;
    private double ISC;

    public BufferSolute(BufferSolution bufferSolution, BufferingAgent bufferingAgent, double concentration)
    {
        this.bufferSolution = bufferSolution;
        this.bufferingAgent = bufferingAgent;
        this.concentration = concentration;
        ISC = CalculateISC(this);
    }

    private double CalculateISC(BufferSolute bufferSolute)
    {
        double f = Math.pow(10, bufferSolute.bufferSolution.getpH() - bufferSolute.bufferingAgent.getpKa());
        double ISC = bufferSolute.concentration * (1 / (1 + f)) / 2;
        if (bufferSolute.bufferingAgent.getIsAnionic()) ISC *= f;
        return ISC;
    }

    public BufferSolution getBufferSolution ()
    {
        return bufferSolution; 
    }

    public BufferingAgent getBufferingAgent ()
    {
        return bufferingAgent; 
    }

    public double concentration ()
    {
        return concentration; 
    }

    public double getISC ()
    {
        return ISC; 
    }
}
