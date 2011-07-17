package org.bio.calculator.calciumbuffer.client;

import org.bio.calculator.calciumbuffer.client.solution.*;
import org.bio.calculator.calciumbuffer.client.solute.*;
import org.bio.calculator.calciumbuffer.client.buffering_agent.BufferingAgent;
import org.bio.calculator.calciumbuffer.client.ion.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Calculator extends Composite {

	private static CalculatorUiBinder uiBinder = GWT
			.create(CalculatorUiBinder.class);
	@UiField Label lblTitle;
	@UiField TextArea txtCalciumTotal;
	@UiField TextArea txtEGTATotal;
	@UiField TextArea txtMagnesiumTotal;
	@UiField TextArea txtPhLevel;
	@UiField Button btnCalculate;
	@UiField VerticalPanel pnlResult;

	interface CalculatorUiBinder extends UiBinder<Widget, Calculator> {
	}

	public Calculator() {
		initWidget(uiBinder.createAndBindUi(this));
		lblTitle.setStylePrimaryName("title");
	}

	@UiHandler("btnCalculate")
	void onBtnCalculateClick(ClickEvent event) {

		

		
		//
		// define metals, ligands, buffer agents
		//

		Metal[][] Metals = null;
		Metals[0][0] = new Metal("H" , 1, 1);
		Metals[1][0] = new Metal("Li", 2, 1);
		Metals[1][1] = new Metal("Be", 2, 2);
		Metals[2][0] = new Metal("Na", 3, 1);
		Metals[2][1] = new Metal("Mg", 3, 2);
		Metals[3][0] = new Metal("K" , 4, 1);
		Metals[3][1] = new Metal("Ca", 4, 2);
		Metals[4][0] = new Metal("Rb", 5, 1);
		Metals[4][1] = new Metal("Sr", 5, 2);
		Metals[5][0] = new Metal("Cs", 6, 1);
		Metals[5][1] = new Metal("Ba", 6, 2);
		Metals[6][0] = new Metal("Fr", 7, 1);
		Metals[6][1] = new Metal("Ra", 7, 2);
		
		double[][][] ZeroKs = null;
		for (int row = 0; row < 7; row++)
		{
			for (int col = 0; col < 2; col++)
			{
				for (int N = 0; N < 4; N++)
				{
					ZeroKs[row][col][N] = 0;
				}
			}
		}
		
		double [][][] EGTA_Ks = ZeroKs;
		// H
		EGTA_Ks[0][0][0] = 9.46;
		EGTA_Ks[0][0][1] = 8.85;
		EGTA_Ks[0][0][2] = 2.68;
		EGTA_Ks[0][0][3] = 2.00;
		// Ca
		EGTA_Ks[3][1][0] = 10.716;
		EGTA_Ks[3][1][1] = 5.33;
		// Mg
		EGTA_Ks[2][1][0] = 5.21;
		EGTA_Ks[2][1][1] = 3.37;
		// Sr
		EGTA_Ks[4][1][0] = 8.5;
		EGTA_Ks[4][1][1] = 4.37;

		double[][][] ATP_Ks = ZeroKs;
		// H
		ATP_Ks[0][0][0] = 6.95;
		ATP_Ks[0][0][1] = 4.05;
		ATP_Ks[0][0][2] = 1;
		ATP_Ks[0][0][3] = 1;
		// Ca
		ATP_Ks[3][1][0] = 3.982;
		ATP_Ks[3][1][1] = 1.8;
		// Mg
		ATP_Ks[2][1][0] = 4.324;
		ATP_Ks[2][1][1] = 2.74;
		// Sr
		ATP_Ks[4][1][0] = 3.6;
		ATP_Ks[4][1][1] = 2.05;
		// K
		ATP_Ks[3][0][0] = 0.903;
		ATP_Ks[3][0][1] = -.3;		
		// Na
		ATP_Ks[2][0][0] = 0.944;
		ATP_Ks[2][0][1] = 0.602;		
		// Li
		ATP_Ks[1][0][0] = 1.69;
		ATP_Ks[1][0][1] = 0.778;
		
		double[][][] CP_Ks = ZeroKs;
		// H
		CP_Ks[0][0][0] = 4.58;
		CP_Ks[0][0][1] = 2.7;
		// Ca
		CP_Ks[3][1][0] = 1.15;
		// Mg
		CP_Ks[2][1][0] = 1.3;
		// Sr
		CP_Ks[4][1][0] = 1.08;
		
		double[][][] HDTA_Ks = ZeroKs;
		// H
		HDTA_Ks[0][0][0] = 10.81;
		HDTA_Ks[0][0][1] = 9.79;
		HDTA_Ks[0][0][2] = 2.7;
		HDTA_Ks[0][0][3] = 2.20;
		// Ca
		HDTA_Ks[3][1][0] = 4.6;
		HDTA_Ks[3][1][1] = 3.7;
		// Mg
		HDTA_Ks[2][1][0] = 4.8;
		HDTA_Ks[2][1][1] = 3.66;
		
		double[][][] EDTA_Ks = ZeroKs;
		// H
		EDTA_Ks[0][0][0] = 10.26;
		EDTA_Ks[0][0][1] = 6.16;
		EDTA_Ks[0][0][2] = 2.67;
		EDTA_Ks[0][0][3] = 1.99;
		// Ca
		EDTA_Ks[3][1][0] = 10.7;
		EDTA_Ks[3][1][1] = 3.51;
		// Mg
		EDTA_Ks[2][1][0] = 8.69;
		EDTA_Ks[2][1][1] = 2.28;
		// Sr
		EDTA_Ks[4][1][0] = 8.63;
		EDTA_Ks[4][1][1] = 2.3;
		
		double[][][] ADP_Ks = ZeroKs;
		// H
		ADP_Ks[0][0][0] = 6.68;
		ADP_Ks[0][0][1] = 3.99;
		ADP_Ks[0][0][2] = 1.00;
		// Ca
		ADP_Ks[3][1][0] = 2.81;
		ADP_Ks[3][1][1] = 1.52;
		// Mg
		ADP_Ks[2][1][0] = 3;
		ADP_Ks[2][1][1] = 1.45;
		// Sr
		ADP_Ks[4][1][0] = 2.5;
		ADP_Ks[4][1][1] = 1.34;
		
		double[][][] Oxalate_Ks = ZeroKs;
		// H
		Oxalate_Ks[0][0][0] = 3.81;
		Oxalate_Ks[0][0][1] = 1.37;
		// Ca
		Oxalate_Ks[3][1][0] = 3;
		// Mg
		Oxalate_Ks[2][1][0] = 2.55;
		// Sr
		Oxalate_Ks[4][1][0] = 2.54;
		
		double[][][] Phosphate_Ks = ZeroKs;
		// H
		Phosphate_Ks[0][0][0] = 6.71;
		Phosphate_Ks[0][0][1] = 2.1;
		// Ca
		Phosphate_Ks[3][1][0] = 1.7;
		// Mg
		Phosphate_Ks[2][1][0] = 1.88;
		// Sr
		Phosphate_Ks[4][1][0] = 1.52;
		// K
		Phosphate_Ks[3][0][0] = .49;		
		// Na
		Phosphate_Ks[2][0][0] = 0.6;	
		// Li
		Phosphate_Ks[1][0][0] = .72;
		
		Ligand[] Ligands = null;
		Ligands[0] = new Ligand("ATP" , 	2 ,ATP_Ks		);
		Ligands[1] = new Ligand("EGTA", 	4 ,EGTA_Ks		);
		Ligands[2] = new Ligand("HDTA", 	4 ,HDTA_Ks		);
		Ligands[3] = new Ligand("CP"  , 	2 ,CP_Ks		);
		Ligands[4] = new Ligand("EDTA", 	4 ,EDTA_Ks		);
		Ligands[5] = new Ligand("ADP" , 	3 ,ADP_Ks		);
		Ligands[6] = new Ligand("Oxalate", 	2 ,Oxalate_Ks	);
		Ligands[7] = new Ligand("Phosphate",2 ,Phosphate_Ks );
		
		BufferingAgent[] BufferingAgents = null;
		BufferingAgents[0] = new BufferingAgent("TES", 7.4, true);
	
		//
		// normally all ingredients should be added via drop-downs with text boxes for concentrations and buttons for free/total
		//
		
		BufferSolution myBuffSol = new BufferSolution(7.1, .160, 25);   /* pH, ionic strength, temp just now specified */
		myBuffSol.Add(new MetalSolute( myBuffSol, Metals[3][1] 			/* Ca   */	, 0.000000010	, IonSolute.State.free));
		myBuffSol.Add(new MetalSolute( myBuffSol, Metals[4][1] 			/* Sr   */	, 0.000001   	, IonSolute.State.free));
		myBuffSol.Add(new MetalSolute( myBuffSol, Metals[2][1] 			/* Mg   */	, 0.00316228 	, IonSolute.State.free)); 
		myBuffSol.Add(new LigandSolute(myBuffSol, Ligands[0]   			/* ATP  */	, 0.00316228	, IonSolute.State.free));
		myBuffSol.Add(new LigandSolute(myBuffSol, Ligands[1]   			/* EGTA */	, 0.005			, IonSolute.State.total));
		myBuffSol.Add(new LigandSolute(myBuffSol, Ligands[2]   			/* HDTA */	, 0.005			, IonSolute.State.total));
		myBuffSol.Add(new LigandSolute(myBuffSol, Ligands[3]   			/* CP   */	, 0.012			, IonSolute.State.total));
		myBuffSol.Add(new BufferSolute(myBuffSol, BufferingAgents[0]	/* TES */	, 0.300								   ));
		

		/*
		 * can't just enter however they want, it's not a whole species (e.g. K2SrEGTA) that is being controlled, 
		 * it's the EGTA total and the Sr free, so it's reasonable to specify roles as an M2+E and M2+/Mg.  the math 
		 * for each is identical though, so can iterate the equations first and decide what was added as what later
		 */
	
		myBuffSol.Iterate(.000001);  //argument is convergence limit
		
		/* 
		 * above should result in buffer of Fabiato & Fabiato (1979; program 2 testing) and give 
	     *	
		 *	[CP]total = 12 mM, added as Na2CP, so [Na2CP]added = 12 mM
		 *	[K2CaEGTA]added = 160.971 uM
		 *	[Ca]total = 160.987 uM so [CaCl2]added = 16 nM
		 *	[K2SrEGTA]added = 99.486 uM
		 *	[Sr]total = 100.811 uM so [SrCl2]added = 1.325 uM
		 *   [KCl]added = 58.734 mM
		 *	[Na2ATP]added = 3.295 mM
		 *	[Mg]added = 7.862 mM 
		 *	TES 30 mM
		 *	15 mM KOH
		 *	[K2H2EGTA]total = 5 mM, [K2H2HDTA]total = 5 mM
		 *	[NaCl]added = 30.590 mM = 2[Na2CP]+2[Na2ATP]
		 */
		
		pnlResult.clear();
		Grid tblResults = new Grid(2, 9);
		tblResults.setBorderWidth(3);
		tblResults.setCellPadding(1);

		tblResults.setHTML(0, 0, "<b>Sr total</b>");
		tblResults.setHTML(0, 1, "<b>ATP total</b>");
		tblResults.setHTML(0, 2, "<b>Mg total</b>");
		tblResults.setHTML(0, 3, "<b>Ca total</b>");
		tblResults.setHTML(0, 4, "<b>KCl total</b>");	
		tblResults.setHTML(0, 5, "<b>K total</b>");
		tblResults.setHTML(0, 6, "<b>Na total</b>");
		tblResults.setHTML(0, 7, "<b>SrEGTA</b>");
		tblResults.setHTML(0, 8, "<b>CaEGTA</b>");
		
		tblResults.setHTML(1, 0, myBuffSol.getAnionSoluteList()[0].concentration);
		tblResults.setHTML(1, 1, myBuffSol.getLigandSoluteList()[0].concentration);
		tblResults.setHTML(1, 2, myBuffSol.getAnionSoluteList()[1].concentration);
		tblResults.setHTML(1, 3, myBuffSol.getAnionSoluteList()[2].concentration);
		tblResults.setHTML(1, 4, myBuffSol.getAnionSoluteList()[0].concentration);
		tblResults.setHTML(1, 5, myBuffSol.getAnionSoluteList()[0].concentration);
		tblResults.setHTML(1, 6, myBuffSol.getAnionSoluteList()[0].concentration);
		tblResults.setHTML(1, 7, myBuffSol.getAnionSoluteList()[0].concentration);
		tblResults.setHTML(1, 8, myBuffSol.getAnionSoluteList()[0].concentration);
		
		pnlResult.add(tblResults);
		pnlResult.add(new Label("CaEGTA = 5.73493e-06"));
		pnlResult.add(new Label("MgEGTA = 6.31558e-08"));
		Label lblConvergence = new Label("Convergence");
		lblConvergence.setStylePrimaryName("convergence");
		pnlResult.add(lblConvergence);
	}
}
