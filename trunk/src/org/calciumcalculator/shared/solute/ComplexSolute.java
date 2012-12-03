package org.calciumcalculator.shared.solute;
import org.calciumcalculator.shared.species.Ligand;

public class ComplexSolute extends LigandSolute
{
    private Double Kapp;
    private MetalSolute metalSolute;
    private LigandSolute ligandSolute;
    private Double CSum;
    
    public ComplexSolute(LigandSolute newLigandSolute, MetalSolute newMetalSolute)
    {
// a ComplexSolute is a LigandSolute:MetalSolute complex, with an apparent K encompassing the ladder of Ks as more metals progressively bind, 
// and with its mean square charge, and thus ionic strength contribution, reduced by the corresponding negation of charge
        
        super (newLigandSolute.getBufferSolution(), (Ligand)newLigandSolute.getSpecies(), 0.0, false);
        metalSolute = newMetalSolute;
        ligandSolute = newLigandSolute;
        int N = getLigand().getKsFor(metalSolute.getSpecies().getName()).length;
        CSum = CPLXCalculateSUM(N);        
        Kapp = CSum/ligandSolute.getSUM();
        
        if (CSum>0)
        {
            setCharge(Math.pow((CPLXCalculateWeightedSUM(N) / CSum), 0.5)); //i.e. root mean square charge, depends only on pH & various Ks
        }
        else
        {
            setCharge(0.0);
        }
    }
    
    public MetalSolute getMetalSolute()
    {
        return metalSolute;
    }
    
    private Double CPLXCalculateSUM(int numTerms) // calculated single time upon addition to buffer
    {
        Double result = 0.0;
        for (int counter = 0; counter < numTerms; counter++) 
        {
            result += CPLXCalculateSUMTerm(counter);
        }
        return result;
    }

    private Double CPLXCalculateWeightedSUM(int numTerms) { // calculated single time upon addition to buffer
        Double result = 0.0;

        for (int counter = 0; counter < numTerms; counter++) 
        {
            result += CPLXCalculateWeightedSUMTerm(counter);
        }

        return result;
    } 

    private double CPLXCalculateSUMTerm(int N) 
    {
        double K = 1.0;
        for (int counter=0;counter<N;counter++){
            K *= this.getLigand().getKsFor("H")[counter];
        }
        K *= Math.pow(getBufferSolution().getH(), N);
        K *= getLigand().getKsFor(metalSolute.getSpecies().getName())[N];
        return K;
    }

    private Double CPLXCalculateWeightedSUMTerm(int N) { 
        double K;
        K = CPLXCalculateSUMTerm(N) * Math.pow(this.getLigand().getValence() - metalSolute.getSpecies().getValence() - N, 2); 
        return K;
    }
    
    @Override
    public Double updateConcentration()
    {
        setNewFreeConcentration(Kapp * ligandSolute.getFreeConcentration() * metalSolute.getFreeConcentration());
        Double delta = getNewFreeConcentration() - getFreeConcentration();
        setFreeConcentration(getNewFreeConcentration());
        setTotalConcentration(getFreeConcentration());
        return delta;
    }
    
    public Double getKapp()
    {
        return Kapp; 
    }
    
    @Override
    public void recalculateProperties()
    {
        int N = getLigand().getKsFor(metalSolute.getSpecies().getName()).length;
        Double newCSum = CPLXCalculateSUM(N);  
        ligandSolute.recalculateSUM();
        Double newKapp = newCSum/ligandSolute.getSUM();
        setCSum(newCSum);
        setKapp(newKapp);
        if (CSum>0){
            setCharge(Math.pow((CPLXCalculateWeightedSUM(N) / CSum), 0.5)); //i.e. root mean square charge, depends only on pH & various Ks
        } else {
            setCharge(0.0);
        }
    }
    
    public Double getCSum()
    {
        return CSum; 
    }
    
    public void setCSum(Double newCSum)
    {
        CSum = newCSum; 
    }
    
//     @Override
//     public Species getSpecies(){
//         return getMetalSolute().getSpecies();
//     }
    
    public void setKapp(Double newKapp)
    {
        Kapp = newKapp; 
    }
    
    @Override
    public String toString(){
        return "cplx w/ "+metalSolute.getSpecies().getName()+
            ":  []: "+prettyNum(getFreeConcentration())+
            "; charge: "+prettyNum(getCharge())+
            "; Kapp: "+prettyNum(Kapp)+
            "; ISC: "+prettyNum(getISC());
    }
    
    @Override
    public boolean equals(Object soluteObject)
    {
        ComplexSolute otherSolute = (ComplexSolute)soluteObject;
        return this.getSpecies().equals(otherSolute.getSpecies())&&this.getMetalSolute().getSpecies().equals(otherSolute.getMetalSolute().getSpecies());
    }
}
