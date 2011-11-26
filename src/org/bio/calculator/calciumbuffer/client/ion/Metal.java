package org.bio.calculator.calciumbuffer.client.ion;

public class Metal extends Ion {
	
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