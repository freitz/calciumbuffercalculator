package org.bio.calculator.calciumbuffer.client.ion;

public class Ligand extends Ion {

        private double[][][] K;  //Ks for metal in row, col; indices are row, column, N

        public Ligand(String name, int valence, double[][][] K)
        {
        	super(name, valence, Type.ligand);
            this.K = K;
        }

        public double[][][] getK()
        {
            return K; 
        }
}
