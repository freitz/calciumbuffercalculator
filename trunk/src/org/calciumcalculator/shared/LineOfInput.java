package org.calciumcalculator.shared;
import org.calciumcalculator.shared.species.Species;

public class LineOfInput
{
    public enum Tag {
        goal, buffer, degC, ionicStrength, pH, total, p, pMg, pMgA, pME, unknown;
        
        public static Tag fromString(String name) {
        	for(Tag tag : values()) {
        		if(tag.name().equals(name)) {
        			return tag;
        		}
        	}
        	return null;
        }
    }    
    
    private Tag tag;
    private Double value;
    private Species[] speciesArray;
    
    public LineOfInput(Tag newTag, Double newValue, Species[] newSpeciesArray)
    {
        tag = newTag;
        value = newValue;
        speciesArray = newSpeciesArray;
    }
    
    public Tag getTag()
    {
        return tag;
    }
    
    public Double getValue()
    {
        return value;
    }
    
    public void setValue(Double newValue)
    {
        value = newValue;
    }
    
    public Species[] getSpeciesArray()
    {
        return speciesArray;
    }
    
    public String toString()
    {
    	String result;
    	result =tag.toString()+" "+value.toString();
    	if (tag==Tag.total){
    		result+="mM ";
    	}
    	result += " species:[";
    	for(Species s : speciesArray) {
    		result+=s.toString()+",";
    	}
    	result += "]";
    	return result;
    
    }
}
