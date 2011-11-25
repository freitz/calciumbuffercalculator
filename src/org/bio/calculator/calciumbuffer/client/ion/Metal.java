package org.bio.calculator.calciumbuffer.client.ion;

import java.util.Map;

public class Metal extends Ion {
	
    private int row;
    private int column;
    
    //private Map<Ligand, Double[]> kConstants = null;
    
    public Metal(String name, int row, int column)
    {
    	super (name, column, Type.metal);
        this.row = row;
        this.column = column;
     }

     public int getRow() {
          return row; 
     }

     public int getColumn() {
         return column; 
     }
}