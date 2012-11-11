package org.calciumcalculator.shared.solute;

import org.calciumcalculator.shared.solution.BufferSolution;
import org.calciumcalculator.shared.species.BufferingAgent;

public class BufferSolute extends Solute
{
// A BufferSolute is a Solute of a BufferingAgent, inheriting corresponding properties, but also 
// with a means of calculating its ionic strength contribution that depends upon pH, pKa, and the
// sign of the charge of the buffering agent (i.e. whether it is cationic or anionic)

    private BufferingAgent bufferingAgent;
    private Double ISC;  

    public BufferSolute(BufferSolution newBufferSolution, BufferingAgent newBufferingAgent, Double newConcentration)
    {
        super(newBufferSolution, newBufferingAgent, newConcentration, false);
        bufferingAgent = newBufferingAgent;
        calculateISC(); // for 30 mM TES @ pH 7.21922, IS 160 mM, temp = 22 deg C, ISC should be 5.634 mM
    }

    private void calculateISC() {
        Double f = Math.pow(10, getBufferSolution().getpH() - bufferingAgent.getpKa());
        ISC = this.getTotalConcentration() * (1 / (1 + f)) / 2;
        if (bufferingAgent.isAnionic()) ISC *= f;
    }
    
    public Double getISC(){
        return ISC;
    }
}
