package org.bio.calculator.calciumbuffer.client.solute;

public class LigandSolute : IonSolute
{
    private Ligand _ligand;
    private double _meanSquareCharge;
    private BufferSolution _bufferSolution;
    private double _apparentFreeToTrueFreeRatio;
    private ComplexSolute[,] _complexSolutes;  // 6 x 2 array for metals in row, col

    public LigandSolute (BufferSolution bufferSolution, Ligand ligand, double concentration, state state):base(bufferSolution,ligand,concentration,state)
    {
        _ligand = ligand;
        _bufferSolution = bufferSolution;
        _apparentFreeToTrueFreeRatio = GetApparentFreeToTrueFreeRatio(this);
        _meanSquareCharge = GetApparentFreeToTrueFreeRatio(this, true) / _apparentFreeToTrueFreeRatio;
    }

    private double GetFree()
    {
        return this.totalConcentration / GetSumBoundPerFree();
    }

    private double GetTotal()
    {
        return this.freeConcentration * GetSumBoundPerFree();
    }

    public double GetSumBoundPerFree()
    {
        double result = 1;
        foreach (MetalSolute metalSolute in this._bufferSolution.metalSoluteList)
        {
            if (metalSolute.metal.column == periodicTableColumn.IIa)
            {
                result += this.complexSolutes[metalSolute.metal.row, (int)metalSolute.metal.column].Kapp * metalSolute.freeConcentration;
            }
        }
        return result;
    }

    private double GetApparentFreeToTrueFreeRatio(LigandSolute ligandSolute, Boolean weightByCharge = false)
    {
        double K = 1;
        double result = 0;
        int count = ligandSolute.ligand.KH.Length + 1;

        if (weightByCharge) { count--; }

        for (int counter = 0; counter < count; counter++)
        {
            result += K;
            if (weightByCharge) { result *= Math.Pow(ligandSolute.ligand.valence - counter, 2); }
            K *= ligandSolute.ligand.KH[counter] * ligandSolute.bufferSolution.H;
        }
        return result;
    }

    public Ligand ligand
    {
        get { return _ligand; }
        set { _ligand = value; }
    }

    public double apparentFreeToTrueFreeRatio
    {
        get { return _apparentFreeToTrueFreeRatio; }
        //set { _apparentFreeToTrueFreeRatio = value; }
    }

    public ComplexSolute[,] complexSolutes
    {
        get { return _complexSolutes; }
        set { _complexSolutes = value; }
    }
}
