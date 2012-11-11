package org.calciumcalculator.shared.solute;

import java.util.ArrayList;

import org.calciumcalculator.shared.solution.BufferSolution;
import org.calciumcalculator.shared.species.Ligand;
import org.calciumcalculator.shared.species.Species;

public class LigandSolute extends Solute
{
// a LigandSolute is a Solute that can bind MetalSolutes.  as each LigandSolute can bind more than one
// MetalSolute, each LigandSolute keeps a list of such ComplexSolutes that it exists in

    private Double SUM; //i.e., apparent-free-conc to true-free-conc ratio, can be calculated upon construction
    private ArrayList<ComplexSolute> complexes;
    private Double[] myKsForH; // myKsForH = this.getLigand().getKsFor("H"), for convenience.  

    public LigandSolute (BufferSolution newBufferSolution, Ligand ligand, Double concentration, boolean targetIsFree) 
    {
        this(newBufferSolution, ligand, concentration, targetIsFree, null);
    }
    
    public LigandSolute (BufferSolution newBufferSolution, Ligand ligand, Double concentration, boolean targetIsFree, Species[] newCofactorArray) 
    {
        super(newBufferSolution, ligand, concentration, targetIsFree, newCofactorArray);
        myKsForH = this.getLigand().getKsFor("H");
        complexes = new ArrayList<ComplexSolute>();
        setSUM(calculateSUM(myKsForH.length));
    }

    public void calculateSumAndCharge() // happens upon addition to buffer rather than immediately lest ComplexSolutes get null pointer exceptions upon construction
    {
        setCharge(Math.sqrt(calculateWeightedSUM(myKsForH.length) / getSUM())); //i.e., mean square charge
    }
    
    public void recalculateProperties()
    {
        int L = myKsForH.length;
        setSUM(calculateSUM(L));
        setCharge(Math.sqrt(calculateWeightedSUM(L) /  getSUM()));
        for (ComplexSolute complexSolute : getComplexes()){
            complexSolute.recalculateProperties();
        }
               
    }
    
    public void addComplex(ComplexSolute complex) 
    {
        int index;
        ComplexSolute currentComplex;
        if (complexes.contains(complex)){
            index = complexes.indexOf(complex);
            currentComplex = complexes.get(index);
            currentComplex.setTotalConcentration(currentComplex.getTotalConcentration() + complex.getTotalConcentration());
            complexes.set(index, currentComplex);
            System.out.println("added twice"+complex);
        } else {
            complexes.add(complex);
        }
    }

    @Override
    public Double getTotalToFreeRatio() { // depends on free concentrations so happens during iteration
        Double result = 1.0;
        for (ComplexSolute complex : getComplexes()){
//             if (complex.getMetalSolute().getSpecies().getValence() == 2){
                result += complex.getKapp() * complex.getMetalSolute().getFreeConcentration();
//             }
        }
        return result;
    }

    private Double calculateSUM(int numTerms) // calculated single time upon addition to buffer
    {
        Double result = 0.0;
        for (int counter = 0; counter <= numTerms; counter++){
            result += calculateSUMTerm(counter);
        }
        return result;
    }

    private Double calculateWeightedSUM(int numTerms){ // calculated single time upon addition to buffer
        Double result = 0.0;

        for (int counter = 0; counter < numTerms; counter++){
            result += calculateWeightedSUMTerm(counter);
        }

        return result;
    } 

    private double calculateSUMTerm(int N) 
    {
        double K = 1.0;
        for (int counter=0;counter<N;counter++){
            K *= myKsForH[counter];
        }
        K *= Math.pow(getBufferSolution().getH(), N);
        return K;
    }

    private Double calculateWeightedSUMTerm(int N) { 
        return calculateSUMTerm(N) * Math.pow(this.getLigand().getValence() - N, 2); 
    }

    public ArrayList<ComplexSolute> getComplexes() {
        return complexes;
    }

    public void setComplexes(ArrayList<ComplexSolute> newComplexes) {
        complexes = newComplexes;
    }

    public void setSUM(Double newSum) {
        SUM = newSum;
    }

    public Ligand getLigand() {
        return (Ligand)this.getSpecies();
    }

    public Double getSUM() {
        return SUM;
    }
    
    public void recalculateSUM() {
        //TODO: make a private process
    }
    
}