package org.calciumcalculator.shared.solute;
import java.util.Comparator;

import org.calciumcalculator.shared.solution.BufferSolution;
import org.calciumcalculator.shared.species.Species;

public class Solute //extends Species (would extend but there are different species types
{
// once a Species is associated with a buffersolution it becomes a "Solute" [MAYBE SHOULD EXTEND?],
// gaining the properties of free concentration (not bound to other solutes), total concentration (free + bound),
// total-to-free ratio, charge (ionic form stabilized by polar solvent), ionic strength contribution, and 
// program-specific properties "lastConcentration" (concentration it had during last iteration of calculations,
// useful for judging convergence), and "targetIsFree", denoting whether the goal is to know what total concentration
// produced a given free concentration or what free concentration a given total would result in

    private Species species;
    private BufferSolution bufferSolution;
    private Double freeConcentration = 0.0;
    private Double newFreeConcentration = 0.0;
    private Double totalConcentration = 0.0;
    private Double newTotalConcentration = 0.0;
    private Double charge;
    private Double ISC = 0.0;
    private Double totalToFreeRatio = 1.0;
    private boolean targetIsFree;
    Species[] cofactorArray;
    
    public Solute(BufferSolution newBufferSolution, Species newSpecies, Double newConcentration, boolean targetPopulationIsFree){
        this(newBufferSolution, newSpecies, newConcentration,targetPopulationIsFree, null);
    }
    
    public Solute(BufferSolution newBufferSolution, Species newSpecies, Double newConcentration, boolean targetPopulationIsFree, Species[] newCofactorArray){
        bufferSolution = newBufferSolution;
        charge = (Double)(double) newSpecies.getValence();
        species = newSpecies;
        cofactorArray = newCofactorArray;
        if (species.getType() == Species.Type.anion) {
            charge = - charge;
        }
        targetIsFree=targetPopulationIsFree;
        if (targetIsFree){
            freeConcentration = newConcentration;
        } 
        else {
            totalConcentration = newConcentration;
        }
    }
    
    public Double updateConcentration() {
        Double delta;
        if (targetIsFree){
            newTotalConcentration =  freeConcentration * getTotalToFreeRatio();
            delta = newTotalConcentration - totalConcentration;
            totalConcentration = newTotalConcentration;
        }else{
            newFreeConcentration = totalConcentration / getTotalToFreeRatio();
            delta = newFreeConcentration - freeConcentration;
            freeConcentration = newFreeConcentration;
        }
        return delta;
    }
    
    public class SoluteComparator implements Comparator<Solute> {
        @Override
        public int compare(Solute s1, Solute s2) {
            return s1.getSpecies().getName().compareTo(s2.getSpecies().getName());
        }
    }
    
    public Double getTotalToFreeRatio() {
        return totalToFreeRatio;
    }
   
    public Double getCharge() {
        return charge; 
    }
    
    public void setCharge (Double newCharge) {
        charge = newCharge; 
    }
    
    public Double getFreeConcentration() {
        return freeConcentration; 
    }
    
    public void setFreeConcentration (Double newFreeConcentration) {
        freeConcentration = newFreeConcentration; 
    }

    public Double getTotalConcentration(){
        return totalConcentration; 
    }
    
    public Double getNewFreeConcentration(){
        return newFreeConcentration; 
    }
    
    public void setNewFreeConcentration(Double newNewFreeConcentration){
        newFreeConcentration = newNewFreeConcentration;
    }

    public void setTotalConcentration (Double newTotalConcentration){
        totalConcentration = newTotalConcentration; 
    }
    
    public Double getISC() { // depends on current free concentration, so happens during iteration
        return freeConcentration * Math.pow(charge, 2)/2;
    }

    public Species getSpecies() {
        return species;
    }
    
    public Species[] getCofactorArray() {
        return cofactorArray;
    }
    
    public boolean getTargetIsFree() {
        return targetIsFree;
    }
    
    public void setCofactorArray(Species[] newCofactorArray) {
        cofactorArray = newCofactorArray;
    }
    
    @Override
    public String toString() {
        return "species: " + this.getSpecies().getName() +
        ", free: "  + prettyNum(getFreeConcentration())+
        ", total: " + prettyNum(getTotalConcentration())+
        ", charge: "+ prettyNum(getCharge())+
        ", ISC: "   + prettyNum(getISC());
    }

    public String prettyNum(Double number){
    	return number.toString();
    }
    
    public BufferSolution getBufferSolution() {
        return bufferSolution;
    }
    
    @Override
    public boolean equals(Object soluteObject)
    {
        Solute otherSolute = (Solute)soluteObject;
        return this.getSpecies().equals(otherSolute.getSpecies());
    }
}