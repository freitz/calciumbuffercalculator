package org.bio.calculator.calciumbuffer.client.species;

public class BufferingAgent extends Species
{
    private double pKa;
    private boolean isCationic;
	
    public BufferingAgent (String name, double pKa, boolean isCationic)
    {
    	super(name, 0, Type.bufferingAgent);
        this.pKa = pKa;
        this.isCationic = isCationic;
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