package org.bio.calculator.calciumbuffer.client.solute;

public class ComplexSolute
{
    private double _Kapp;
    private double _meanSquareCharge;
    private double _ISC;
    private LigandSolute _ligandSolute;
    private Metal _metal;
    private double _concentration;

    public ComplexSolute(LigandSolute ligandSolute, Metal metal)
    {
        _ligandSolute = ligandSolute;
        _metal = metal;
        _Kapp = GetKapp();
        _meanSquareCharge = GetKapp(true) / _Kapp;
    }

    private double GetKapp(Boolean weightByCharge = false)
    {
        double result = 0;
        double K = 1;

        for (int counter = 0; counter < 2; counter++)
        {
            result += _ligandSolute.ligand.KM[_metal.row, (int)_metal.column, counter] * K * Math.Pow(_ligandSolute.bufferSolution.H, counter);
            if (weightByCharge) { result *= Math.Pow(_ligandSolute.ligand.valence - _metal.valence - counter, 2); }
            K *= _ligandSolute.ligand.KH[counter];
        }

        return result / (_ligandSolute.apparentFreeToTrueFreeRatio * _Kapp);
    }

    public void Update()
    {
        _concentration = GetConcentration();
        _ISC = GetISC();
    }

    private double GetConcentration()
    {
        return 0; ////////////////////////////////////////////////////////////////////
    }

    private double GetISC()
    {
        return 0; ////////////////////////////////////////////////////////////////////
    }

    public double Kapp
    {
        get { return _Kapp; }
        //set { _Kapp = value; }
    }

    public double ISC
    {
        get { return _ISC; }
        //set { _ISC = value; }
    }
}
