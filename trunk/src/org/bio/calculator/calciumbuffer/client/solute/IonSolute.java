package org.bio.calculator.calciumbuffer.client.solute;

import org.bio.calculator.calciumbuffer.client.ion.Ion;
import org.bio.calculator.calciumbuffer.client.solution.BufferSolution;

public class IonSolute
{
    protected BufferSolution bufferSolution;
//    private Ion ion;
    protected Double freeConcentration;
    protected Double totalConcentration;
    
    public enum State {
    	total, free
    }
    
    protected State state;
//    private Double sumBoundFree;
    protected Double ISC;
    
    public IonSolute(BufferSolution bufferSolution, Ion ion, Double concentration, State state)
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

    public Double getFreeConcentration()
    {
        return freeConcentration; 
    }
    
    public void setFreeConcentration (Double newFreeConcentration)
    {
        freeConcentration = newFreeConcentration; 
    }

    public Double getTotalConcentration()
    {
        return totalConcentration; 
    }

    public void setTotalConcentration (Double newTotalConcentration)
    {
        totalConcentration = newTotalConcentration; 
    }

    public Double getISC ()
    {
        return ISC; 
    }
    
    public void setISC (Double newISC)
    {
    	ISC = newISC; 
    }
}