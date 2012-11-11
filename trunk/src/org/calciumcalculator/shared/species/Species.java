package org.calciumcalculator.shared.species;

public class Species {

//  "Species" is the atomic/molecular identity of an atom or molecule.
//  this class encompasses the properties of metals and anions for the
//  purposes of this program, while ligands and buffering agents have 
//  additional properties warranting subclassing.

    public enum Type {
        ligand, metal, anion, bufferingAgent, unknown
    }
    
    private String name;
    private int valence;
    private Type type;
    
    public Species(String newName, int newValence, Type newType)
    {
        name = newName;
        valence = newValence;
        type = newType;
    }
    
    public String getName() {
        return name;
    }

    public int getValence() {
        return valence;
    }
    
    public Type getType() {
        return type;
    }
  
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object speciesObject)
    {
        Species otherSpecies = (Species)speciesObject;
        return this.getName().compareTo(otherSpecies.getName())==0;
    }
}
