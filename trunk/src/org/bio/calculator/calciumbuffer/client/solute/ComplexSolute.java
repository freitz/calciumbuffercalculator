package org.bio.calculator.calciumbuffer.client.solute;

public class ComplexSolute extends LigandSolute
{
    private Double Kapp;
    private MetalSolute metalSolute;
    private LigandSolute ligandSolute;
    
    public ComplexSolute(LigandSolute ligandSolute, MetalSolute metalSolute)
    {
    	super (ligandSolute.getBufferSolution(), ligandSolute.getLigand(), 0.0, Solute.State.total);
    	SUM = calculateSUM(2);
        charge = calculateWeightedSUM(2) / SUM; //i.e. mean square charge
        Kapp = SUM/ligandSolute.SUM; 
    }
  
    protected Double calculateSUMTerm(int N)
    {
    	return super.calculateSUMTerm(N) * ligandSolute.getLigand().getKsFor(metalSolute.getMetal())[N];
    }
    
    protected Double calculateWeightedSUMTerm(int N)
    {
    	return this.calculateSUMTerm(N) * (ligandSolute.getLigand().getValence() - metalSolute.getMetal().getValence());
    }
    
    public Double update()
    {
    	Double delta;
    	totalConcentration = Kapp * ligandSolute.freeConcentration * metalSolute.freeConcentration;
    	freeConcentration = totalConcentration;
        delta = totalConcentration - lastConcentration;
        ISC = calculateISC();
        return delta;
    }
    
    public Double getKapp()
    {
        return Kapp; 
    } 
}
