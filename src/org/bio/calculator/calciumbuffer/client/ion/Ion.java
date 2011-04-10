package org.bio.calculator.calciumbuffer.client.ion;

public class Ion {
	public enum Type {
		ligand, metal, anion, unknown
	}
	
    private String _name;
    private int _valence;
    private Type _type;
    
	public void setName(String _name) {
		this._name = _name;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setValence(int valence) {
		this._valence = valence;
	}
	
	public int getValence() {
		return _valence;
	}
	
	public void setType(Type type) {
		this._type = type;
	}
	
	public Type getType() {
		return _type;
	}
}
