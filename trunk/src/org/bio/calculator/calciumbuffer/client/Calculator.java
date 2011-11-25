package org.bio.calculator.calciumbuffer.client;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bio.calculator.calciumbuffer.client.solution.*;
import org.bio.calculator.calciumbuffer.client.solute.*;
import org.bio.calculator.calciumbuffer.client.buffering_agent.BufferingAgent;
import org.bio.calculator.calciumbuffer.client.ion.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.util.collect.HashMap;
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
	//public static final String Magnesium = "MG";

	private static CalculatorUiBinder uiBinder = GWT
			.create(CalculatorUiBinder.class);
	@UiField Label lblTitle;
	@UiField TextArea txtpM2E;
	@UiField TextArea txtpM2;
	@UiField TextArea txtpMg;
	@UiField TextArea txtpATP;
	@UiField TextArea txtEGTATotal;
	@UiField TextArea txtHDTATotal;
	@UiField TextArea txtCPTotal;
	@UiField TextArea txtpH;
	@UiField TextArea txtTESTotal;
	@UiField TextArea txtKOHAdded;
	@UiField TextArea txtIonicStrength;
	
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

		//TODO:  define metals, solutes, update & ISC for solutes, add to buffer, iterate, report all in buffersolution lists
		
		//
		// define metals, ligands, buffer agents
		//
		
		Metal H  = new Metal("H" , 1, 1);
		Metal Li = new Metal("Li", 2, 1);
		Metal Be = new Metal("Be", 2, 2);
		Metal Na = new Metal("Na", 3, 1);
		Metal Mg = new Metal("Mg", 3, 2);
		Metal K  = new Metal("K" , 4, 1);
		Metal Ca = new Metal("Ca", 4, 2);
		Metal Rb = new Metal("Rb", 5, 1);
		Metal Sr = new Metal("Sr", 5, 2);
		Metal Cs = new Metal("Cs", 6, 1);
		Metal Ba = new Metal("Ba", 6, 2);
		Metal Fr = new Metal("Fr", 7, 1);
		Metal Ra = new Metal("Ra", 7, 2);
		
		Ligand EGTA = new Ligand("EGTA",null);
		EGTA.addKs(H,  new Double[]{9.46  , 8.85, 2.68, 2.00});
		EGTA.addKs(Ca, new Double[]{10.716, 5.33});
		EGTA.addKs(Mg, new Double[]{5.21  , 3.37});
		EGTA.addKs(Sr, new Double[]{8.50  , 4.37});		
		
		Ligand ATP = new Ligand("ATP",null);
		ATP.addKs(H,  new Double[]{6.95 , 4.05, 1.00, 1.00});
		ATP.addKs(Ca, new Double[]{3.982, 1.80});
		ATP.addKs(Mg, new Double[]{4.324, 2.74});
		ATP.addKs(Sr, new Double[]{3.60 , 2.05});
		ATP.addKs(K,  new Double[]{0.903,-0.30});
		ATP.addKs(Na, new Double[]{0.944, 0.602});
		ATP.addKs(Li, new Double[]{1.69 , 0.778});

		Ligand CP = new Ligand("CP",null);
		CP.addKs(H,  new Double[]{4.58, 2.70});
		CP.addKs(Ca, new Double[]{1.15});
		CP.addKs(Mg, new Double[]{1.3});
		CP.addKs(Sr, new Double[]{1.08});	
		
		Ligand HDTA = new Ligand("HDTA",null);
		HDTA.addKs(H,  new Double[]{10.81, 9.79, 2.70, 2.20});
		HDTA.addKs(Ca, new Double[]{4.60 , 3.70});
		HDTA.addKs(Mg, new Double[]{4.80 , 3.66});
		
		Ligand EDTA = new Ligand("EDTA",null);
		EDTA.addKs(H,  new Double[]{10.26, 6.16, 2.67, 1.99});
		EDTA.addKs(Ca, new Double[]{10.70, 3.51});
		EDTA.addKs(Mg, new Double[]{8.69 , 2.28});
		EDTA.addKs(Sr, new Double[]{8.63 , 2.30});	
		
		Ligand ADP = new Ligand("ADP",null);
		ADP.addKs(H,  new Double[]{6.68, 3.99, 1.00});
		ADP.addKs(Ca, new Double[]{2.81, 1.52});
		ADP.addKs(Mg, new Double[]{3.00, 1.45});
		ADP.addKs(Sr, new Double[]{2.50, 1.34});	
		
		Ligand C2H2O4 = new Ligand("C2H2O4",null);
		C2H2O4.addKs(H,  new Double[]{3.81, 1.37});
		C2H2O4.addKs(Ca, new Double[]{3.00});
		C2H2O4.addKs(Mg, new Double[]{2.55});
		C2H2O4.addKs(Sr, new Double[]{2.54});	
		
		Ligand HPO4 = new Ligand("HPO4",null);
		HPO4.addKs(H,  new Double[]{6.71, 2.1});
		HPO4.addKs(Ca, new Double[]{1.70});
		HPO4.addKs(Mg, new Double[]{1.88});
		HPO4.addKs(Sr, new Double[]{1.52});
		HPO4.addKs(K,  new Double[]{0.49});
		HPO4.addKs(Na, new Double[]{0.60});
		HPO4.addKs(Li, new Double[]{0.72});
		
		BufferingAgent TES = new BufferingAgent("TES", 7.4, true);
		
		//if(ATPKs.containsKey(Magnesium)) { // if this ligand has a K for MG
	
		//
		// normally all ingredients should be added via drop-downs with text boxes for concentrations and buttons for free/total
		//
		
		BufferSolution myBuffSol = new BufferSolution(7.1, .160, 25);   /* pH, ionic strength, temp just now specified */
		myBuffSol.Add(new MetalSolute( myBuffSol, Ca  , 0.000000010, IonSolute.State.free ));
		myBuffSol.Add(new MetalSolute( myBuffSol, Sr  , 0.000001   , IonSolute.State.free ));
		myBuffSol.Add(new MetalSolute( myBuffSol, Mg  , 0.00316228 , IonSolute.State.free )); 
		myBuffSol.Add(new LigandSolute(myBuffSol, ATP , 0.00316228 , IonSolute.State.free ));
		myBuffSol.Add(new LigandSolute(myBuffSol, EGTA, 0.005	   , IonSolute.State.total));
		myBuffSol.Add(new LigandSolute(myBuffSol, HDTA, 0.005	   , IonSolute.State.total));
		myBuffSol.Add(new LigandSolute(myBuffSol, CP  , 0.012	   , IonSolute.State.total));
		myBuffSol.Add(new BufferSolute(myBuffSol, TES , 0.300		                      ));	
	
		myBuffSol.Iterate(.000001);  //argument is convergence limit
		
		/* 
		 * above should result in buffer of Fabiato & Fabiato (1979; program 2 testing) and give 
	     *	
		 *	[CP]total = 12 mM, added as Na2CP, so [Na2CP]added = 12 mM
		 *	[K2CaEGTA]added = 160.971 uM
		 *	[Ca]total = 160.987 uM so [CaCl2]added = 16 nM
		 *	[K2SrEGTA]added = 99.486 uM
		 *	[Sr]total = 100.811 uM so [SrCl2]added = 1.325 uM
		 *  [KCl]added = 58.734 mM
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
		/*
		tblResults.setHTML(1, 0, myBuffSol.getMetalSoluteList().get(0).getTotalConcentration().toString());
		tblResults.setHTML(1, 1, myBuffSol.getMetalSoluteList().get(1).getTotalConcentration().toString());
		tblResults.setHTML(1, 2, myBuffSol.getMetalSoluteList().get(2).getTotalConcentration().toString());
		tblResults.setHTML(1, 3, myBuffSol.getLigandSoluteList().get(0).getTotalConcentration().toString());
		tblResults.setHTML(1, 4, myBuffSol.getLigandSoluteList().get(1).getTotalConcentration().toString());
		tblResults.setHTML(1, 5, myBuffSol.getLigandSoluteList().get(2).getTotalConcentration().toString());
		tblResults.setHTML(1, 6, myBuffSol.getLigandSoluteList().get(3).getTotalConcentration().toString());
		*/
		tblResults.setHTML(1, 7, "???");
		tblResults.setHTML(1, 8, "???");
		
		pnlResult.add(tblResults);
		pnlResult.add(new Label("hello"));
		Label lblConvergence = new Label("Convergence");
		lblConvergence.setStylePrimaryName("convergence");
		pnlResult.add(lblConvergence);
	}
}
