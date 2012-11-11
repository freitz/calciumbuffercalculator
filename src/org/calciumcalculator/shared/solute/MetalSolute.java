package org.calciumcalculator.shared.solute;

import org.calciumcalculator.shared.solution.BufferSolution;
import org.calciumcalculator.shared.species.Species;

public class MetalSolute extends Solute
{
// a MetalSolute is a Solute that can form complexes with LigandSolutes, and accordingly its 
// total-to-free ratio is calculated using the list of ligands available to bind with

    public MetalSolute (BufferSolution bufferSolution, Species metal, double concentration, boolean targetIsFree)
    {
       this (bufferSolution, metal, concentration,targetIsFree, null);
    }

    public MetalSolute (BufferSolution bufferSolution, Species metal, double concentration, boolean targetIsFree, Species[] newCofactorArray)
    {
        super (bufferSolution, metal, concentration, targetIsFree, newCofactorArray);
    }

    @Override
    public Double getTotalToFreeRatio() // done during iteration -- needs current free concentrations
    {
        Double result = 1.0;
        LigandSolute ligandSolute;

        for (LigandSolute ligand : getBufferSolution().getLigandList()){
            for (ComplexSolute complex : ligand.getComplexes()){
                if (complex.getMetalSolute()==this){
                    result += complex.getKapp() * ligand.getFreeConcentration();
                }
            }
        }
        return result;
    }
}
