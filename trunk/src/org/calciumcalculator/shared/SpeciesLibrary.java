package org.calciumcalculator.shared;

import java.util.HashMap;
import java.util.Map;
import org.calciumcalculator.shared.species.BufferingAgent;
import org.calciumcalculator.shared.species.Ligand;
import org.calciumcalculator.shared.species.Species;
import org.calciumcalculator.shared.species.Species.Type;

public class SpeciesLibrary 
{
	public static Map<String, Species> BUFFERENTRIES = null;
	public static Map<String, Species> NONBUFFERENTRIES = null;
	public static Map<String, Species> ENTRIES = null;
	
	public synchronized static Map<String, Species> getAllEntries() {
		if(ENTRIES == null) {
			ENTRIES = new HashMap<String, Species>();
	    		    	
	    	ENTRIES.put("H" ,H);
	    	ENTRIES.put("Li", Li);
	    	ENTRIES.put("Be", Be);
	    	ENTRIES.put("Na", Na);
	    	ENTRIES.put("Mg", Mg);
	    	ENTRIES.put("K" , K);
	    	ENTRIES.put("Ca", Ca);
	    	ENTRIES.put("Rb", Rb);
	    	ENTRIES.put("Sr", Sr);
	    	ENTRIES.put("Cs", Cs);
	    	ENTRIES.put("Ba", Ba);
	    	ENTRIES.put("Fr", Fr);
	    	ENTRIES.put("Ra", Ra);
	    	ENTRIES.put("Cl", Cl);
	    	ENTRIES.put("CP",CP);
	    	ENTRIES.put("HDTA",HDTA);
	        ENTRIES.put("EDTA",EDTA);
	    	ENTRIES.put("ADP",ADP);
	    	ENTRIES.put("C2H2O4",C2H2O4);
	    	ENTRIES.put("HPO4",HPO4);
	    	ENTRIES.put(EGTA.getName(), EGTA);
	    	ENTRIES.put(ATP.getName(), ATP);
	    	ENTRIES.put(TES.getName(), TES);
	    	ENTRIES.put(Imidazole.getName(), Imidazole);
	    	ENTRIES.put(Tris.getName(), Tris);
		}
		return ENTRIES;
	}
	
	public synchronized static Map<String, Species> getBufferEntries() {//to add another buffering agent, add a put line here, in getAllentries, and a declaration at the bottom with other buffering agents
		if(BUFFERENTRIES == null) {
			BUFFERENTRIES = new HashMap<String, Species>();
	    	BUFFERENTRIES.put(TES.getName(), TES);
	    	BUFFERENTRIES.put(Imidazole.getName(), Imidazole);
	    	BUFFERENTRIES.put(Tris.getName(), Tris);
		}
		return BUFFERENTRIES;
	}
	
	public synchronized static Map<String, Species> getNonBufferEntries() {
		if(NONBUFFERENTRIES == null) {
			NONBUFFERENTRIES = new HashMap<String, Species>();
			
			NONBUFFERENTRIES.put("H" ,H);
			NONBUFFERENTRIES.put("Li", Li);
			NONBUFFERENTRIES.put("Be", Be);
			NONBUFFERENTRIES.put("Na", Na);
			NONBUFFERENTRIES.put("Mg", Mg);
			NONBUFFERENTRIES.put("K" , K);
			NONBUFFERENTRIES.put("Ca", Ca);
			NONBUFFERENTRIES.put("Rb", Rb);
			NONBUFFERENTRIES.put("Sr", Sr);
			NONBUFFERENTRIES.put("Cs", Cs);
			NONBUFFERENTRIES.put("Ba", Ba);
			NONBUFFERENTRIES.put("Fr", Fr);
			NONBUFFERENTRIES.put("Ra", Ra);
			NONBUFFERENTRIES.put("Cl", Cl);
			NONBUFFERENTRIES.put("CP",CP);
			NONBUFFERENTRIES.put("HDTA",HDTA);
			NONBUFFERENTRIES.put("EDTA",EDTA);
			NONBUFFERENTRIES.put("ADP",ADP);
			NONBUFFERENTRIES.put("C2H2O4",C2H2O4);
			NONBUFFERENTRIES.put("HPO4",HPO4);
		}
		return NONBUFFERENTRIES;
	}

//  this class is a catalog of metals and commonly used ligands 
//  and buffers assembled for ready use by the calcium calculator.  
//  each such atomic/molecular "species" is instantiated with its 
//  corresponding properties.

    public static Species H  = new Species("H" , 1, Type.metal);
    public static Species Li = new Species("Li", 1, Type.metal);
    public static Species Be = new Species("Be", 2, Type.metal);
    public static Species Na = new Species("Na", 1, Type.metal);
    public static Species Mg = new Species("Mg", 2, Type.metal);
    public static Species K  = new Species("K" , 1, Type.metal);
    public static Species Ca = new Species("Ca", 2, Type.metal);
    public static Species Rb = new Species("Rb", 1, Type.metal);
    public static Species Sr = new Species("Sr", 2, Type.metal);
    public static Species Cs = new Species("Cs", 1, Type.metal);
    public static Species Ba = new Species("Ba", 2, Type.metal);
    public static Species Fr = new Species("Fr", 1, Type.metal);
    public static Species Ra = new Species("Ra", 2, Type.metal);
    public static Species Cl = new Species("Cl", 1, Type.anion);
    //public static Species Cl = new Species("Cl", -1, Type.metal);
    
    public static Ligand EGTA = new Ligand("EGTA", 4);
    {
        EGTA.addKs("H",  new Double[]{9.46 , 8.85, 2.68, 2.00});
        EGTA.addKs("Ca", new Double[]{10.716, 5.33});
        EGTA.addKs("Mg", new Double[]{5.21  , 3.37});
        EGTA.addKs("Sr", new Double[]{8.50  , 4.37}); 
    }

    public static Ligand ATP = new Ligand("ATP", 4);
    {
        ATP.addKs("H",  new Double[]{6.95 , 4.05, 1.00, 1.00});
        ATP.addKs("Ca", new Double[]{3.982, 1.80});
        ATP.addKs("Mg", new Double[]{4.324, 2.74});
        ATP.addKs("Sr", new Double[]{3.60 , 2.05});
        ATP.addKs("K",  new Double[]{0.903,-0.30});
        ATP.addKs("Na", new Double[]{0.944, 0.602});
        ATP.addKs("Li", new Double[]{1.69 , 0.778});
    }

    public static Ligand CP = new Ligand("CP", 2);
    {
        CP.addKs("H",  new Double[]{4.58, 2.70});
        CP.addKs("Ca", new Double[]{1.15});
        CP.addKs("Mg", new Double[]{1.3});
        CP.addKs("Sr", new Double[]{1.08}); 
    }
    
    public static Ligand HDTA = new Ligand("HDTA", 4);
    {
        HDTA.addKs("H",  new Double[]{10.81, 9.79, 2.70, 2.20});
        HDTA.addKs("Ca", new Double[]{4.60 , 3.70});
        HDTA.addKs("Mg", new Double[]{4.80 , 3.66});
    }
    
    public static Ligand EDTA = new Ligand("EDTA", 4);
    {
        EDTA.addKs("H",  new Double[]{10.26, 6.16, 2.67, 1.99});
        EDTA.addKs("Ca", new Double[]{10.70, 3.51});
        EDTA.addKs("Mg", new Double[]{8.69 , 2.28});
        EDTA.addKs("Sr", new Double[]{8.63 , 2.30});    
    }
    
    public static Ligand ADP = new Ligand("ADP", 3);
    {
        ADP.addKs("H",  new Double[]{6.68, 3.99, 1.00});
        ADP.addKs("Ca", new Double[]{2.81, 1.52});
        ADP.addKs("Mg", new Double[]{3.00, 1.45});
        ADP.addKs("Sr", new Double[]{2.50, 1.34});  
    }
    
    public static Ligand C2H2O4 = new Ligand("C2H2O4", 2);
    {
        C2H2O4.addKs("H",  new Double[]{3.81, 1.37});
        C2H2O4.addKs("Ca", new Double[]{3.00});
        C2H2O4.addKs("Mg", new Double[]{2.55});
        C2H2O4.addKs("Sr", new Double[]{2.54}); 
    }

    public static Ligand HPO4 = new Ligand("HPO4", 2);
    {
        HPO4.addKs("H",  new Double[]{6.71, 2.1});
        HPO4.addKs("Ca", new Double[]{1.70});
        HPO4.addKs("Mg", new Double[]{1.88});
        HPO4.addKs("Sr", new Double[]{1.52});
        HPO4.addKs("K",  new Double[]{0.49});
        HPO4.addKs("Na", new Double[]{0.60});
        HPO4.addKs("Li", new Double[]{0.72});
    }
    
    public static BufferingAgent TES = new BufferingAgent("TES", 7.44, false); // isCationic = false
    public static BufferingAgent Imidazole = new BufferingAgent("Imidazole", 7.06, true);
    public static BufferingAgent Tris = new BufferingAgent("Tris", 8.37, true); 
    
    public SpeciesLibrary(){
    }
}
