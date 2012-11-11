package org.calciumcalculator.shared.solution;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.calciumcalculator.shared.SpeciesLibrary;
import org.calciumcalculator.shared.solute.BufferSolute;
import org.calciumcalculator.shared.solute.ComplexSolute;
import org.calciumcalculator.shared.solute.LigandSolute;
import org.calciumcalculator.shared.solute.MetalSolute;
import org.calciumcalculator.shared.solute.Solute;
import org.calciumcalculator.shared.species.Ligand;
import org.calciumcalculator.shared.species.Species;
import org.calciumcalculator.shared.species.Species.Type;

public class BufferSolution {

    private Double pH;
    private Double H;
    private Double temperature; // in Celsius
    private Double ionicStrength;
    private ArrayList<MetalSolute> metalList;
    private ArrayList<LigandSolute> ligandList;   
    private BufferSolute bufferSolute;
    private Double currentIonicStrength;
    private Double currentError;
    private Double totalKClAdded = 0.0;

    public BufferSolution(Double newpH, Double newIonicStrength, Double newTemperature)
    {
        pH = newpH;
        temperature = newTemperature;
        ionicStrength = newIonicStrength;
        H = CalculateH(pH, temperature, ionicStrength);
        metalList = new ArrayList<MetalSolute>();
        ligandList = new ArrayList<LigandSolute>();
    }

    public void populateLigandSoluteComplexes() // call only after populating metalList, ligandList
    {
        LigandSolute tempLigand;
        for (int ligandCounter=0;ligandCounter<ligandList.size();ligandCounter++){
            tempLigand=ligandList.get(ligandCounter);
            for (MetalSolute metal : metalList){
                tempLigand.addComplex(new ComplexSolute(tempLigand,metal));
            }
            ligandList.set(ligandCounter, tempLigand);
        }
    }

    public boolean iterate(Double convergenceCriterion, int iterationsLimit, boolean verbose) 
    {
        boolean convergence = false;
        boolean stop = false;
        int iteration = 0;
        Double ionicStrengthGap;
        Species.Type type;
        Double bufferIonicStrength = getIonicStrength();

        populateLigandSoluteComplexes();
        
        while (!stop)
        {
            iteration++;
            currentIonicStrength = bufferSolute.getISC();
            currentError = 0.0;
            ionicStrengthGap = 0.0;

            if (verbose) System.out.print("iteration "+Integer.toString(iteration)+": ");

            metalList = updateMetalSoluteList(metalList);
            ligandList = updateLigandSoluteList(ligandList);

            for (LigandSolute ligand: ligandList){
                ligand.setComplexes(updateComplexSoluteList(ligand.getComplexes()));
            }

            convergence = (currentError <= convergenceCriterion);
            stop = (convergence || (iteration > iterationsLimit));

            if (bufferIonicStrength > 0.0){
                ionicStrengthGap = bufferIonicStrength - currentIonicStrength;
                add(new MetalSolute(this, SpeciesLibrary.K , ionicStrengthGap/2, false));
                add(new MetalSolute(this, SpeciesLibrary.Cl , ionicStrengthGap/2, false));   
                totalKClAdded += ionicStrengthGap/2;
                //false means "total" as opposed to "free". variable is "targetPopulationIsFree"            } 
            } else {
                setH(CalculateH(getpH(), getTemperature(), currentIonicStrength)); 
                //needs to trickle back down to complex Kapp, complex MSC, ligand SUM, ligand MSC; 
                for (LigandSolute ligandSolute: ligandList){
                    ligandSolute.recalculateProperties();
                    for (ComplexSolute complexSolute : ligandSolute.getComplexes()){
                            complexSolute.recalculateProperties();
                    }
                }
            }                
            
            if (verbose){
                System.out.print("IS gap = " + Double.toString(ionicStrengthGap) + ", ");
                System.out.println("error = "  + Double.toString(currentError));
            }
            
            // TODO:  currently adding Cl as "MetalSolute"?  because a metal is just a species like anions are, so yes it works, but...
        }
        
        if (bufferIonicStrength == 0.0){
            setIonicStrength(currentIonicStrength);
        } 
        
        if (verbose){
            System.out.println();
            System.out.println("                               ***");
            System.out.println(totalKClAdded+" KCl");
            System.out.println();
        }
        
        sortBuffer();
        
        return convergence;
    }

    private ArrayList<LigandSolute> updateLigandSoluteList(ArrayList<LigandSolute> soluteList)
    {
        int index;
        Double delta;
        boolean targetIsFree;
        for (LigandSolute solute: soluteList)
        {
            index = soluteList.indexOf(solute);
            delta = solute.updateConcentration();
            currentError += Math.abs(delta);
            currentIonicStrength += solute.getISC();
            soluteList.set(index,solute);
            targetIsFree = solute.getTargetIsFree();
            if (targetIsFree){
                for (Species species: solute.getCofactorArray()){
                    switch (species.getType()){
                        case metal:  
                        case anion:
                            add(new MetalSolute(this, species , delta, false));
                            break;
                        case ligand:
                            add(new LigandSolute(this, (Ligand)species , delta, false));
                            break;
                        case bufferingAgent:
                        case unknown:
                        default:
                            break;
                    } 
                }
            }
        }  
        return soluteList;
    }

    private ArrayList<MetalSolute> updateMetalSoluteList(ArrayList<MetalSolute> soluteList)
    {
        int index;
        Double delta;
        boolean targetIsFree;
        for (MetalSolute solute: soluteList)
        {
            index = soluteList.indexOf(solute);
            delta = solute.updateConcentration();
            currentError += Math.abs(delta);
            currentIonicStrength += solute.getISC();
            soluteList.set(index,solute);
            targetIsFree = solute.getTargetIsFree();
            if (targetIsFree){
                for (Species species: solute.getCofactorArray()){
                    switch (species.getType()){
                        case metal: 
                        case anion:
                            add(new MetalSolute(this, species , delta, false));
                            break;
                        case ligand:
                            add(new LigandSolute(this, (Ligand)species , delta, false));
                            break;
                        case bufferingAgent:
                        case unknown:
                        default:
                            break;
                    } 
                }
            }
        }  
        return soluteList;
    }
    
    private ArrayList<ComplexSolute> updateComplexSoluteList(ArrayList<ComplexSolute> soluteList)
    {
        int index;
        Double delta;
        boolean targetIsFree;
        for (ComplexSolute solute: soluteList)
        {
            index = soluteList.indexOf(solute);
            delta = solute.updateConcentration();
            currentError += Math.abs(delta);
            currentIonicStrength += solute.getISC();
            soluteList.set(index,solute);
            targetIsFree = solute.getTargetIsFree();
            if (targetIsFree){
                for (Species species: solute.getCofactorArray()){
                    switch (species.getType()){
                        case metal: 
                        case anion:
                            add(new MetalSolute(this, species , delta, false));
                            break;
                        case ligand:
                            add(new LigandSolute(this, (Ligand)species , delta, false));
                            break;
                        case bufferingAgent:
                        case unknown:
                        default:
                            break;
                    } 
                }
            }
        }  
        return soluteList;
    }
    
// 
//  TODO: -- unify above 3 updaters?
//
    
    public void recalculateProperties(){
        setH(CalculateH(getpH(), getTemperature(), getIonicStrength()));
    }

    private Double CalculateH(Double pH, Double temperature, Double ionicStrength)
    {
        Double H;
        H = Math.pow(10,-pH)/ gammaH(temperature, ionicStrength);
        return H;
    }

    private Double gammaH(Double temperature, Double ionicStrength) // per Bers et al. eqn. 15:
    {
        Double gammaH;
        Double B = 0.522932 * Math.exp(0.0327016 * temperature) + 4.015942;
        gammaH = 0.145045 * Math.exp(-B * ionicStrength) + 0.063546 * Math.exp(-43.97704 * ionicStrength) + 0.695634;
        return gammaH;
    }

    public Double getpH()
    {
        return pH; 
    }

    public Double getH()
    {
        return H; 
    }
    
    public Double getTemperature()
    {
        return temperature; 
    }
    
    public void setH(Double newH)
    {
        H = newH; 
    }
    
    public BufferSolute getBufferSolute()
    {
        return bufferSolute; 
    }
    
    public Double getIonicStrength()
    {
        return ionicStrength; 
    }
    
    public void setIonicStrength(Double newIonicStrength)
    {
        ionicStrength = newIonicStrength; 
    }
    
    public ArrayList<MetalSolute> getMetalList()
    {
        return metalList;
    }
    
    public ArrayList<LigandSolute> getLigandList()
    {
        return ligandList;
    }
    
    public ArrayList<LigandSolute> addToLigands(LigandSolute newSolute, ArrayList<LigandSolute> soluteList){
        int index;
        Double newConcentration;
        LigandSolute solute;
        if (soluteList.contains(newSolute)){
            index = soluteList.indexOf(newSolute);
            solute = soluteList.get(index);
            newConcentration = solute.getTotalConcentration() + newSolute.getTotalConcentration();
            solute.setTotalConcentration(newConcentration);
            soluteList.set(index, solute);
        } else {
            soluteList.add(newSolute);
        }
        return soluteList;
    }

    public ArrayList<MetalSolute> addToMetals(MetalSolute newSolute, ArrayList<MetalSolute> soluteList){
        int index;
        Double newConcentration;
        MetalSolute solute;

        if (soluteList.contains(newSolute)){
            index = soluteList.indexOf(newSolute);
            solute = soluteList.get(index);
            newConcentration = solute.getTotalConcentration() + newSolute.getTotalConcentration();
            solute.setTotalConcentration(newConcentration);
            soluteList.set(index, solute);
        } else {
            soluteList.add(newSolute);
        }
        return soluteList;
    }
    
    public void add(Solute newSolute)
    {       
        int index;
        LigandSolute tempLigand;
        ArrayList<Solute> soluteList;
        if (newSolute.getSpecies().getType()==Type.ligand){
            tempLigand=(LigandSolute)newSolute;
            tempLigand.calculateSumAndCharge();
            ligandList=addToLigands(tempLigand,ligandList);
        } else if (newSolute.getSpecies().getType()==Type.metal){
            metalList=addToMetals((MetalSolute)newSolute,metalList);
        } else if (newSolute.getSpecies().getType()==Type.bufferingAgent){
            bufferSolute=(BufferSolute)newSolute;
        } else if (newSolute.getSpecies().getType()==Type.anion){
            metalList=addToMetals((MetalSolute)newSolute,metalList);
        } else {
            System.out.println("add() unimplemented for this species type");
        }
    }

    public String prettyNum(Double number){
    	return number.toString();
    }
    
    private void sortBuffer(){
        ArrayList<ComplexSolute> complexList;
        Collections.sort(metalList, new SoluteComparator());
        Collections.sort(ligandList, new SoluteComparator());
        for (LigandSolute ligandSolute : ligandList){
            complexList = ligandSolute.getComplexes();
            Collections.sort(complexList, new ComplexSoluteComparator());
            ligandSolute.setComplexes(complexList);
        }
    }

    private class SoluteComparator implements Comparator<Solute> {
        @Override
        public int compare(Solute s1, Solute s2) {
            return s1.getSpecies().getName().compareTo(s2.getSpecies().getName());
        }
    }
    
    private class ComplexSoluteComparator implements Comparator<ComplexSolute> {
        @Override
        public int compare(ComplexSolute cs1, ComplexSolute cs2) {
            return cs1.getMetalSolute().getSpecies().getName().compareTo(cs2.getMetalSolute().getSpecies().getName());
        }
    }
    
    public String toString()
    {
        String tempString = getBufferSolute().getTotalConcentration() +
            "M "+ getBufferSolute().getSpecies().getName() + 
            " buffer (ISC: "+ prettyNum(getBufferSolute().getISC())+"), pH "+ prettyNum(getpH()) +
            ", total ionic strength "+ prettyNum(getIonicStrength())+"\n\n";
        for (MetalSolute metalSolute : metalList){
            tempString+=metalSolute.toString()+"\n";
        }
        for (LigandSolute ligandSolute : ligandList){
            tempString+=ligandSolute.toString()+"\n";
            for (ComplexSolute complexSolute : ligandSolute.getComplexes()){
                if (complexSolute.getFreeConcentration() > 0.0){
                    tempString+="\t"+complexSolute.toString()+"\n";
                }
            }
        }
        return tempString;
    }
}
