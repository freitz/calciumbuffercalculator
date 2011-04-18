package org.bio.calculator.calciumbuffer.client.ion;

public class Metal extends KeyIon {

	public enum PeriodicTableColumn {
		Ia, IIa
	}
	
    private int _row;
    private PeriodicTableColumn _column;
    

    
    public Metal(String name, int row, PeriodicTableColumn column)
            : base(name, (int)column + 1, ionType.metal)
     {
         _row = row;
         _column = column;
     }

     public int getRow() {
          return row; 
     }

     public PeriodicTableColumn getColumn() {
         return _column; 
     }
	
	
}