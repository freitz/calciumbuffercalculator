package org.bio.calculator.calciumbuffer.client.ion;

public class Metal extends Ion {

//	public enum PeriodicTableColumn {
//		Ia, IIa
//	}
	
    private int row;
    private int column;
    
    public Metal(String name, int row, int column)
    {
    	super ( name, column, Type.metal);
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