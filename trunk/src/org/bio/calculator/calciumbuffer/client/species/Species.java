package org.bio.calculator.calciumbuffer.client.species;

public class Species {

	public enum Type {
		ligand, metal, anion, bufferingAgent, unknown
	}
    
	private String name;
    protected int valence;
    protected Type type;
    
	public Species(String name, int valence, Type type)
	{
		this.name = name;
		this.valence = valence;
		this.type = type;
	}
	
	public void setName(String ionName) {
		this.name = ionName;
	}
	
	public String getName() {
		return name;
	}

	public int getValence() {
		return valence;
	}
	
	public void setType(Type speciesType) {
		this.type = speciesType;
	}
	
	public Type getType() {
		return type;
	}
    
    @Override
    public String toString() {
    	return name;
    }
}
