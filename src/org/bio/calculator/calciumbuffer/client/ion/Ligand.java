package org.bio.calculator.calciumbuffer.client.ion;

import java.util.HashMap;
import java.util.Map;

public class Ligand extends Ion {

        private Map<String, Double[]> Ks = null;
        
        public Ligand(String name, Map<String, Double[]> ks, int valence) {
        	super(name, valence, Type.ligand);
        	this.setName(name);
        	this.Ks = ks;
        	if(Ks == null) {
        		Ks = new HashMap<String, Double[]>();
        	}
        }
        
        public void addKs(Metal metal, Double[] ks) //throws Exception
        {
        	//if(kConstants.containsKey(metal)) {
        	//	throw new Exception("k-constants are already defined for Ligand " + this + " and metal " + metal);
        	//}
        	Ks.put(metal.getName(), ks);
        }
        
        // returns null if k-constants don't exist for the Metal
        public Double[] getKsFor(Metal m) {
        	return getKsFor(m.getName());        
        }
        
        public Double[] getKsFor(String metalName) {
        	return getKsFor(metalName);        
        }
}
