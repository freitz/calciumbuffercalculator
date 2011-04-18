package org.bio.calculator.calciumbuffer.client.solute;

public class BufferSolute
{
    private BufferSolution _bufferSolution;
    private BufferingAgent _bufferingAgent;
    private double _concentration;
    private double _ISC;

    public BufferSolute(BufferSolution bufferSolution, BufferingAgent bufferingAgent, double concentration)
    {
        _bufferSolution = bufferSolution;
        _bufferingAgent = bufferingAgent;
        _concentration = concentration;
        _ISC = CalculateISC(this);
    }

    private double CalculateISC(BufferSolute bufferSolute)
    {
        double f = Math.Pow(10, bufferSolute.bufferSolution.pH - bufferSolute.bufferingAgent.pKa);
        double ISC = bufferSolute.concentration * (1 / (1 + f)) / 2;
        if (bufferSolute.bufferingAgent.isAnionic) ISC *= f;
        return ISC;
    }

    public BufferSolution bufferSolution
    {
        get { return _bufferSolution; }
        //set { _bufferSolution = value; }
    }

    public BufferingAgent bufferingAgent
    {
        get { return _bufferingAgent; }
        //set { _bufferingAgent = value; }
    }

    public double concentration
    {
        get { return _concentration; }
        //set { _concentration = value; }
    }

    public double ISC
    {
        get { return _ISC; }
        //set { _ISC = value; }
    }
}
