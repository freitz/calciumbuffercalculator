package org.calciumcalculator.shared.species;

public class BufferingAgent extends Species
{
//  a "BufferingAgent" is a molecular species used to set and stabilize
//  the pH of a solution, making it a "buffer solution" or simply a "buffer".  
//  as the contribution of this agent to the ionic strength of a solution 
//  depends upon its pKa (the negative log of its acid association constant) 
//  and whether it is anionic or cationic, these properties extend those of
//  the base class "Species".   

    private double pKa;
    private boolean isCationic;
	
    public BufferingAgent (String name, double newpKa, boolean newIsCationic)
    {
    	super(name, 0, Type.bufferingAgent);
        pKa = newpKa;
        isCationic = newIsCationic;
    }

    public double getpKa() {
        return pKa; 
    }

    public boolean isCationic () {
        return isCationic;
    }

    public boolean isAnionic() {
        return !isCationic;
    }
	
}