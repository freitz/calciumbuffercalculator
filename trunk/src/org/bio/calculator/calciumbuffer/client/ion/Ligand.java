package org.bio.calculator.calciumbuffer.client.ion;

import java.util.HashMap;
import java.util.Map;

public class Ligand extends Ion {

        //private double[][][] K;  //Ks for metal in row, col; indices are row, column, N 
        
        private Map<Metal, Double[]> kConstants = null;
        
        public Ligand(String name, Map<Metal, Double[]> ks) {
        	super();
        	this.setName(name);
        	kConstants = ks;
        	if(kConstants == null) {
        		kConstants = new HashMap<Metal, Double[]>();
        	}
        }
        
        public void addKConstants(Metal metal, Double[] ks) throws Exception {
        	if(kConstants.containsKey(metal)) {
        		throw new Exception("k-constants are already defined for Ligand " + this + " and metal " + metal);
        	}
        	kConstants.put(metal, ks);
        }
        
        // returns null if k-constants don't exist for the Metal
        public Double[] getKConstantsFor(Metal m) {
        	return kConstants.get(m);        
        }

//        public Ligand(String name, int valence, double[][][] K)
//        {
//        	super(name, valence, Type.ligand);
//            this.K = K;
//        }
//
//        public double[][][] getK()
//        {
//            return K; 
//        }
}
