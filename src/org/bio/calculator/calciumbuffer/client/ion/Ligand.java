package org.bio.calculator.calciumbuffer.client.ion;

public class Ligand extends KeyIon {


        private double[] _KH;      //Ks for H; index 0-3 for K1, K2, K3, K4
        private double[,,] _KM;    //Ks for metal in row, col; indices are row, column, N

        public Ligand(string name, int valence, double[] KH, double[,,] KM):base(name, valence, ionType.ligand)
        {
            _KH = KH;
            _KM = KM;
        }

        public double[] KH
        {
            get { return _KH; }
            //set { _KH = value; }
        }

        public double[,,] KM
        {
            get { return _KM; }
            //set { _KM = value; }
        }
  
	
}
