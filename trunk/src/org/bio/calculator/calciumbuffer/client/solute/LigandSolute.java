package org.bio.calculator.calciumbuffer.client.solute;

import org.bio.calculator.calciumbuffer.client.ion.Ligand;
import org.bio.calculator.calciumbuffer.client.solution.BufferSolution;

public class LigandSolute extends IonSolute
{
    private Ligand ligand;
    private double meanSquareCharge;
    private BufferSolution bufferSolution;
    double apparentFreeToTrueFreeRatio;
    private ComplexSolute[][] complexSolutes;  // 6 x 2 array for metals in row, col

    public LigandSolute (BufferSolution bufferSolution, Ligand ligand, double concentration, State state) {
    	super(bufferSolution,ligand,concentration,state);
        this.ligand = ligand;
        this.bufferSolution = bufferSolution;
        apparentFreeToTrueFreeRatio = GetApparentFreeToTrueFreeRatio(this);
//        meanSquareCharge = GetApparentFreeToTrueFreeRatio(this, true) / apparentFreeToTrueFreeRatio;
    }

//    private double GetFree()
//    {
//        return this.totalConcentration / GetSumBoundPerFree();
//    }
//
//    private double GetTotal()
//    {
//        return this.freeConcentration * GetSumBoundPerFree();
//    }

    public double GetSumBoundPerFree()
    {
        double result = 1;
        for (MetalSolute metalSolute : this.bufferSolution.getMetalSoluteList())
        {
            if (metalSolute.getMetal().getColumn() == 1) // 0 for column Ia, 1 for column IIa
            {
                result += this.complexSolutes[metalSolute.getMetal().getRow()][metalSolute.getMetal().getColumn()].getKapp() * metalSolute.freeConcentration;
            }
        }
        return result;
    }

    private double GetApparentFreeToTrueFreeRatio(LigandSolute ligandSolute)
    {
    	return GetApparentFreeToTrueFreeRatio(ligandSolute, false);
    }
    
    private double GetApparentFreeToTrueFreeRatio(LigandSolute ligandSolute, Boolean weightByCharge)
    {
        double K = 1;
        double result = 0;
        int count = ligandSolute.ligand.getKH().length + 1;

        if (weightByCharge) { count--; }

        for (int counter = 0; counter < count; counter++)
        {
            result += K;
            if (weightByCharge) { result *= Math.pow(ligandSolute.ligand.getValence() - counter, 2); }
            K *= ligandSolute.ligand.getKH()[counter] * ligandSolute.bufferSolution.getH();
        }
        return result;
    }

	public Ligand getLigand() {
		return ligand;
	}

	public void setLigand(Ligand ligand) {
		this.ligand = ligand;
	}

	public BufferSolution getBufferSolution() {
		return bufferSolution;
	}

	public void setBufferSolution(BufferSolution bufferSolution) {
		this.bufferSolution = bufferSolution;
	}

	public void setComplexSolutes(ComplexSolute[][] complexSolutes) {
		this.complexSolutes = complexSolutes;
	}

	public ComplexSolute[][] getComplexSolutes() {
		return complexSolutes;
	}
}