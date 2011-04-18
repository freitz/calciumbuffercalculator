package org.bio.calculator.calciumbuffer.client.buffering_agent;

public class BufferingAgent {

    private String _name;
    private double _pKa;
    private boolean _isCationic;

    public BufferingAgent (String name, double pKa, boolean isCationic)
    {
        _name = name;
        _pKa = pKa;
        _isCationic = isCationic;
    }

    public String getName() {
        return _name; 
    }

    public double getpKa() {
        return _pKa; 
    }

    public boolean getIsCationic () {
        return _isCationic;
    }

    public boolean getIsAnionic() {
        return !_isCationic;
    }
}
