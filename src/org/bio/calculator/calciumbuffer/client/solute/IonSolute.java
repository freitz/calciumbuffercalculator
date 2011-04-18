package org.bio.calculator.calciumbuffer.client.solute;

public class IonSolute
{
    private BufferSolution _bufferSolution;
    private Ion _ion;
    private double _freeConcentration;
    private double _totalConcentration;
    private state _state;
    private double _sumBoundFree;
    private double _ISC;

    public IonSolute(BufferSolution bufferSolution, Ion ion, double concentration, state state)
    {
        _bufferSolution = bufferSolution;
        _ion = ion;
        _state = state;

        if (state == state.free)
        {
            _freeConcentration = concentration;
        } 
        else
        {
            _totalConcentration = concentration;
        }
    }

    public state state
    {
        get { return _state; }
        //set { _state = value; }
    }

    public BufferSolution bufferSolution
    {
        get { return _bufferSolution; }
        //set { _bufferSolution = value; }
    }

    public double freeConcentration
    {
        get { return _freeConcentration; }
        set { _freeConcentration = value; }
    }

    public double totalConcentration
    {
        get { return _totalConcentration; }
        set { _totalConcentration = value; }
    }

    public double ISC
    {
        get { return _ISC; }
        set { _ISC = value; }
    }
}