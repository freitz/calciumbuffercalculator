package org.bio.calculator.calciumbuffer.client.species;

public class Metal extends Species {
	
    private int column;
    
    public Metal(String name, int column)
    {
    	super (name, column, Type.metal);
        this.column = column;
     }

     public int getColumn() {
         return column; 
     }
}