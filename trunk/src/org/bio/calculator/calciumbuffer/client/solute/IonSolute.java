package org.bio.calculator.calciumbuffer.client.solute;

import org.bio.calculator.calciumbuffer.client.ion.Ion;
import org.bio.calculator.calciumbuffer.client.solution.BufferSolution;

public class IonSolute
{
    protected BufferSolution bufferSolution;
//    private Ion ion;
    protected double freeConcentration;
    protected double totalConcentration;
    
    public enum State {
    	total, free
    }
    
    protected State state;
//    private double sumBoundFree;
    protected double ISC;
    
    public IonSolute(BufferSolution bufferSolution, Ion ion, double concentration, State state)
    {
        this.bufferSolution = bufferSolution;
//        this.ion = ion;
        this.state = state;

        if (state == State.free)
        {
            freeConcentration = concentration;
        } 
        else
        {
            totalConcentration = concentration;
        }
    }

    public State getState ()
    {
        return state; 
    }

    public BufferSolution getBufferSolution()
    {
        return bufferSolution; 
    }

    public double getFreeConcentration()
    {
        return freeConcentration; 
    }
    
    public void setFreeConcentration (double newFreeConcentration)
    {
        freeConcentration = newFreeConcentration; 
    }

    public double getTotalConcentration()
    {
        return totalConcentration; 
    }

    public void setTotalConcentration (double newTotalConcentration)
    {
        totalConcentration = newTotalConcentration; 
    }

    public double getISC ()
    {
        return ISC; 
    }
    
    public void setISC (double newISC)
    {
    	ISC = newISC; 
    }
}