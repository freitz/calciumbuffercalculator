package org.bio.calculator.calciumbuffer.client.ion;

public class Ligand extends Ion {

        private double[] KH;      //Ks for H; index 0-3 for K1, K2, K3, K4
        private double[][][] KM;    //Ks for metal in row, col; indices are row, column, N

        public Ligand(String name, int valence, double[] KH, double[][][] KM)
        {
        	super(name, valence, Type.ligand);
            this.KH = KH;
            this.KM = KM;
        }

        public double[] getKH ()
        {
            return KH; 
            //set { _KH = value; }
        }

        public double[][][] getKM ()
        {
            return KM; 
        }
}
