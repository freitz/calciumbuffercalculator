package org.bio.calculator.calciumbuffer.client.solute;

public class MetalSolute extends IonSolute
{
    private Metal _metal;

    public MetalSolute (BufferSolution bufferSolution, Metal metal, double concentration, state state):base(bufferSolution,metal,concentration,state)
    {
        _metal = metal;
    }

    public void Update()
    {
        if (this.state == state.free)
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
        foreach (LigandSolute ligandSolute in this.bufferSolution.ligandSoluteList)
        {
            result += ligandSolute.complexSolutes[this.metal.row, (int)this.metal.column].Kapp * ligandSolute.freeConcentration;
        }
        return result;
    }

    public Metal metal
    {
        get { return _metal; }
        //set { _metal = value; }
    }
}
