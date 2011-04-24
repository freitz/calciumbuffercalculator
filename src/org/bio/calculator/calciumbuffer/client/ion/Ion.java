package org.bio.calculator.calciumbuffer.client.ion;

public class Ion {

    private String name;
    protected int valence;
    protected Type type;
	
	public Ion()
	{
		this(Type.unknown);
	}
	
	public Ion(Type type)
	{
		this.type = type;
	}
	
	public Ion(String name, int valence, Type type)
	{
		this.name = name;
		this.valence = valence;
		this.type = type;
	}
	
	public enum Type {
		ligand, metal, anion, unknown
	}
    
	public void setName(String ionName) {
		this.name = ionName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setValence(int ionValence) {
		this.valence = ionValence;
	}
	
	public int getValence() {
		return valence;
	}
	
	public void setType(Type ionType) {
		this.type = ionType;
	}
	
	public Type getType() {
		return type;
	}
}
