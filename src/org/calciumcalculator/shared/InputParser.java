package org.calciumcalculator.shared;

import org.calciumcalculator.shared.solution.BufferSolution;
import org.calciumcalculator.shared.solute.BufferSolute;
import org.calciumcalculator.shared.solute.MetalSolute;
import org.calciumcalculator.shared.solute.ComplexSolute;
import org.calciumcalculator.shared.solute.LigandSolute;
import org.calciumcalculator.shared.species.BufferingAgent;
import org.calciumcalculator.shared.species.Species;
import org.calciumcalculator.shared.species.Ligand;
import org.calciumcalculator.shared.SpeciesLibrary;

public class InputParser
{
    private BufferSolution myBuffSol;
    private SpeciesLibrary SpecLib = new SpeciesLibrary(); //needs this to get Kmga once its buffer is far enough along
    private boolean haveGoal = false;
    private boolean haveBufferSolute = false;
    private boolean haveTemperature = false; 
    private boolean haveIonicStrength = false;
    private boolean havepH = false;
    private boolean haveCreatedBuffer = false;
    private boolean havepMg = false;
    private boolean havepMgA = false;
    
    private Double pHToUse;
    private Double ionicStrengthToUse = 0.0;
    private Double temperatureToUse;
    
    private Double Kmga = 0.0;
    private Double pMg = 0.0;
    private Double pMgA = 0.0;
    
    boolean requireIonicStrengthMgAndATP;
    
    public InputParser(){
    }

    public void parse(LineOfInput newLineOfInput)
    {
        switch (newLineOfInput.getTag()) {
            case goal: 
                if (newLineOfInput.getValue() == 2.0) // method of program Fabiato^2 1979 program 2
                {
                    haveGoal = true;
                    requireIonicStrengthMgAndATP = true;  
                } else if (newLineOfInput.getValue() == 3.0) { // method of program Fabiato^2 1979 program 3
                    haveGoal = true;
                    requireIonicStrengthMgAndATP = false;  
                } else {
                    //valid goal not obtained
                }
                break;
            case buffer:
                haveBufferSolute = true;
                myBuffSol.add(new BufferSolute(myBuffSol, (BufferingAgent)newLineOfInput.getSpeciesArray()[0], newLineOfInput.getValue()*0.001));
                break;
            case degC:
                haveTemperature = true;
                temperatureToUse = newLineOfInput.getValue();
                break;
            case ionicStrength:
                haveIonicStrength = true;
                ionicStrengthToUse = newLineOfInput.getValue()*0.001;                
                break;
            case pH:
                havepH = true;
                pHToUse = newLineOfInput.getValue();
                break;
            case total:
                addTotal(newLineOfInput);
                break;
            case pMg:
                havepMg = true;
                pMg = newLineOfInput.getValue();
                newLineOfInput.setSpeciesArray(new Species[]{SpeciesLibrary.getAllEntries().get("Mg"), SpeciesLibrary.getAllEntries().get("Cl"), SpeciesLibrary.getAllEntries().get("Cl")});
                addFree(newLineOfInput);
                break;
            case pMgA:
                if (havepMg && Kmga > 0){ 
                    havepMgA = true;
                    pMgA = newLineOfInput.getValue();
                    newLineOfInput.setValue(-Math.log10(Math.pow(10,(-pMgA + pMg))/Kmga));
                    newLineOfInput.setSpeciesArray(new Species[]{SpeciesLibrary.getAllEntries().get("ATP"), SpeciesLibrary.getAllEntries().get("Mg"), SpeciesLibrary.getAllEntries().get("Na"), SpeciesLibrary.getAllEntries().get("Na")});
                    addFree(newLineOfInput);
                    
                    //TODO:  refresh free ATP target as Kapp of Mg-ATP complex is refined in successive iterations
                }
                break;
            case pME:
            case p:
                addFree(newLineOfInput);
                break;
            default:
                break;
        }
        
        if (!haveCreatedBuffer){
            if ((!requireIonicStrengthMgAndATP)||(requireIonicStrengthMgAndATP && haveIonicStrength)){
                if (havepH && haveTemperature){
                    myBuffSol = new BufferSolution(pHToUse, ionicStrengthToUse, temperatureToUse);
                    haveCreatedBuffer = true;
                    Kmga = getKmga(myBuffSol);
                }
            }
        }
    }

    private void addTotal(LineOfInput newLineOfInput)
    {
        for (Species species : newLineOfInput.getSpeciesArray()){ 
            switch (species.getType()){
                case metal:        
                case anion:
                case unknown:
                    myBuffSol.add(new MetalSolute(myBuffSol, species , newLineOfInput.getValue() * 0.001, false));
                    break;
                case ligand:
                    myBuffSol.add(new LigandSolute(myBuffSol, (Ligand)species , newLineOfInput.getValue() * 0.001, false));
                    break;
                case bufferingAgent:
                default:
                    break;
            } 
        }
    }
    
    private void addFree(LineOfInput newLineOfInput)
    {
        int tempInt = newLineOfInput.getSpeciesArray().length-1;
        Species[] tempArray = new Species[tempInt];
        System.arraycopy(newLineOfInput.getSpeciesArray(), 1, tempArray, 0, tempInt); // removes element [0]
        Double tempDouble = Math.pow(10,-newLineOfInput.getValue());
        
        switch (newLineOfInput.getSpeciesArray()[0].getType()){
            case metal:    
            case anion:
            case unknown:
                myBuffSol.add(new MetalSolute(myBuffSol, newLineOfInput.getSpeciesArray()[0] , tempDouble, true, tempArray));
                break;
            case ligand:
                myBuffSol.add(new LigandSolute(myBuffSol, (Ligand)newLineOfInput.getSpeciesArray()[0], tempDouble, true, tempArray));
                break;
            case bufferingAgent:
            default:
                break;
        } 
    }
    
    private Double getKmga(BufferSolution myBuffSol)
    {
        Double Kapp;
        Double ionicStrength = myBuffSol.getIonicStrength();
        boolean totalToFree = (ionicStrength == 0.0);
        if (totalToFree) myBuffSol.setIonicStrength(0.150);
        myBuffSol.recalculateProperties();
        LigandSolute tempLigandSolute = new LigandSolute(myBuffSol, (Ligand)SpecLib.ATP , 0.0, false);
        MetalSolute tempMetalSolute = new MetalSolute(myBuffSol, SpecLib.Mg , 0.0, false);
        ComplexSolute tempCplxSolute = new ComplexSolute(tempLigandSolute, tempMetalSolute);
        Kapp = tempCplxSolute.getKapp();
        if (totalToFree) myBuffSol.setIonicStrength(0.0);
        //System.out.println("getKmga: " + Kapp);
        return Kapp;
    }
    
    public BufferSolution getBufferSolution()
    {
        return myBuffSol;
    }
}
