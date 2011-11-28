package org.bio.calculator.calciumbuffer.client.solute;

import org.bio.calculator.calciumbuffer.client.solution.BufferSolution;
import org.bio.calculator.calciumbuffer.client.species.Species;

public class Solute
{
    protected BufferSolution bufferSolution;
    protected Double freeConcentration;
    protected Double totalConcentration;
    protected Double lastConcentration = 0.0;
    protected Double charge;
    protected Species species;
    protected State state;
    protected Double ISC;
    public enum State {total, free}
    
    public Solute(BufferSolution bufferSolution, Species newSpecies, Double concentration, State newState)
    {
        species = newSpecies;
        state = newState;

        if (state == State.free)
        {
            freeConcentration = concentration;
        } 
        else
        {
            totalConcentration = concentration;
        }
    }
    
    protected double calculateISC()
    {
        return freeConcentration * Math.pow(charge, 2)/2;
    }
    
    public State getState ()
    {
        return state; 
    }

    public Species getSpecies ()
    {
        return species; 
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
    
    public Double getISC()
    {
    	return ISC;
    }
    
    public Double update()
    {
    	return 0.0;
    }
}