package org.calciumcalculator.shared.species;

import java.util.HashMap;

public class Ligand extends Species {

//  in the context of this program, a "Ligand" (pr. "LYE-gand" or "LIH-gand") 
//  is a molecule that binds to metal ions.  most ligands are able to bind to 
//  any of several different metals, each in varying amounts, e.g. the ligand
//  EGTA can bind up to four hydrogen ions, or up to two magnesium.  the 
//  possibility of simultaneously binding some of each is assumed small and 
//  neglected in this program.  as the number of bound ions influences the 
//  ligand's affinity for binding further ions, each ligand accordingly has
//  an array of binding constants for each metal.  

//  the Ks can be assigned at time of instantiation, after which point the Ligand 
//  per se (as opposed to its Solute form) is fully defined and needs no further 
//  updating

    private HashMap<String, Double[]> Ks;
    
    public Ligand(String name, int valence) {
        super(name, valence, Type.ligand);
        Ks = new HashMap<String, Double[]>();
    }
    
    public Ligand(String name, int valence, HashMap<String, Double[]> newKs) {
        super(name, valence, Type.ligand);
        Ks = newKs;
    }
    
    public void addKs(String metalName, Double[] newKs){
        Double[] tempKs=newKs;
        for (int counter=0;counter<newKs.length;counter++){
            tempKs[counter]=(Double)Math.pow(10,newKs[counter]);
        }
        Ks.put(metalName, tempKs);
    }
    
    public Double[] getKsFor(String metalName){
        Double[] tempList=new Double[]{0.0};
        if (Ks.get(metalName)!=null){
            tempList = Ks.get(metalName);        
        }
        return tempList;
    }
}
