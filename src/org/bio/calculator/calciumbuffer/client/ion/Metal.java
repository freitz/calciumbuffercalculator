package org.bio.calculator.calciumbuffer.client.ion;

public class Metal extends KeyIon {

    private int _row;
    private periodicTableColumn _column;
    
	public enum PeriodicTableColumn {
		Ia, IIa
	}
    
    public Metal(String name, int row, PeriodicTableColumn column)
            : base(name, (int)column + 1, ionType.metal)
     {
         _row = row;
         _column = column;
     }

     public int row
     {
          get { return _row; }
          //set { _row = value; }
     }

     public periodicTableColumn column
     {
         get { return _column; }
         //set { _column = value; }
     }
	
	
}