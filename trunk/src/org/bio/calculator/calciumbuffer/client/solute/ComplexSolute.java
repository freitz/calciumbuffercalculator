package org.bio.calculator.calciumbuffer.client.solute;

//import org.bio.calculator.calciumbuffer.client.ion.Ion.Type;
import org.bio.calculator.calciumbuffer.client.ion.Metal;

public class ComplexSolute
{
    private double Kapp;
    private double meanSquareCharge;
    private double ISC;
    private LigandSolute ligandSolute;
    private Metal metal;
    private double concentration;

    public ComplexSolute(LigandSolute ligandSolute, Metal metal)
    {
        this.ligandSolute = ligandSolute;
        this.metal = metal;
        Kapp = GetKapp();
        meanSquareCharge = GetKapp(true) / Kapp;
    }

    private double GetKapp()
    {
    	return GetKapp(false);
    }
    
    private double GetKapp(Boolean weightByCharge)
    {
        double result = 0;
        double K = 1;

        for (int counter = 0; counter < 2; counter++)
        {
            result += ligandSolute.getLigand().getK()[metal.getRow()][ metal.getColumn()][ counter] * K * Math.pow(ligandSolute.getBufferSolution().getH(), counter);
            if (weightByCharge) { result *= Math.pow(ligandSolute.getLigand().getValence() - metal.getValence() - counter, 2); }
            K *= ligandSolute.getLigand().getK()[0][0][counter];
        }

        return result / (ligandSolute.apparentFreeToTrueFreeRatio * Kapp);
    }

    public void Update()
    {
        this.concentration = GetConcentration();
        ISC = GetISC();
    }

    private double GetConcentration()
    {
        return 0; ////////////////////////////////////////////////////////////////////
    }

    private double GetISC()
    {
        return 0; ////////////////////////////////////////////////////////////////////
    }

    public double getKapp ()
    {
        return Kapp; 
    }

    public double getISC ()
    {
        return ISC; 
    }    
    
    public double getMeanSquareCharge ()
    {
        return meanSquareCharge; 
    }
}
