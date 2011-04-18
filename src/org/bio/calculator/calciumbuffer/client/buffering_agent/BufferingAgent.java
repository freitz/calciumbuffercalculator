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

    public String Name
    {
        get { return _name; }
        //set { _name = value; }
    }

    public double pKa
    {
        get { return _pKa; }
        //set { _pKa = value; }
    }

    public boolean isCationic
    {
        get { return _isCationic; }
        //set { _isCationic = value; }
    }

    public boolean isAnionic
    {
        get { return !_isCationic; }
        //set { _isCationic = !value; }
    }
}