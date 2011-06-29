package org.bio.calculator.calciumbuffer.client;

import org.bio.calculator.calciumbuffer.client.solution.*;
import org.bio.calculator.calciumbuffer.client.solute.*;
import org.bio.calculator.calciumbuffer.client.buffering_agent.BufferingAgent;
import org.bio.calculator.calciumbuffer.client.ion.Metal;

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
		pnlResult.clear();
		Grid tblResults = new Grid(2, 6);
		tblResults.setBorderWidth(3);
		tblResults.setCellPadding(1);
		tblResults.setHTML(0, 0, "<b>Total Ca</b>");
		tblResults.setHTML(0, 1, "<b>Total Mg</b>");
		tblResults.setHTML(0, 2, "<b>Total EGTA</b>");
		tblResults.setHTML(0, 3, "<b>Total pH</b>");
		tblResults.setHTML(0, 4, "<b>Total Free Ca</b>");
		tblResults.setHTML(0, 5, "<b>Total Free Mg</b>");
		tblResults.setHTML(1, 0, "1");
		tblResults.setHTML(1, 1, "1");
		tblResults.setHTML(1, 2, "1");
		tblResults.setHTML(1, 3, "1");
		tblResults.setHTML(1, 4, "0.999994");
		tblResults.setHTML(1, 5, "1");
		pnlResult.add(tblResults);
		pnlResult.add(new Label("CaEGTA = 5.73493e-06"));
		pnlResult.add(new Label("MgEGTA = 6.31558e-08"));
		Label lblConvergence = new Label("Convergence");
		lblConvergence.setStylePrimaryName("convergence");
		pnlResult.add(lblConvergence);
		
		BufferSolution myBuffSol = new BufferSolution(7.1, .160, 25);
		myBuffSol.Add(new MetalSolute(myBuffSol, new Metal("Ca",4,2), 0.000000010, IonSolute.State.free));
		myBuffSol.Add(new MetalSolute(myBuffSol, new Metal("Sr",5,2), 0.000001, IonSolute.State.free));
		myBuffSol.Add(new MetalSolute(myBuffSol, new Metal("Mg",3,2), 0.00316228, IonSolute.State.free));
		myBuffSol.Add(new LigandSolute(myBuffSol, new Ligand("ATP",2,double[],double[][][]), 0.00316228, IonSolute.State.free));
		myBuffSol.Add(new MetalSolute(myBuffSol, new Metal("Na",3,1), 0.00632456, IonSolute.State.total));
		myBuffSol.Add(new LigandSolute(myBuffSol, new Ligand("EGTA",4,double[],double[][][]), 0.005, IonSolute.State.total));
		myBuffSol.Add(new MetalSolute(myBuffSol, new Metal("K",4,1), 0.010, IonSolute.State.total));
		myBuffSol.Add(new LigandSolute(myBuffSol, new Ligand("HDTA",4,double[],double[][][]), 0.005, IonSolute.State.total));
		myBuffSol.Add(new MetalSolute(myBuffSol, new Metal("K",4,1), 0.010, IonSolute.State.total));
		myBuffSol.Add(new LigandSolute(myBuffSol, new Ligand("CP",2,double[],double[][][]), 0.012, IonSolute.State.total));
		myBuffSol.Add(new MetalSolute(myBuffSol, new Metal("Na",3,1), 0.024, IonSolute.State.total));
		myBuffSol.Add(new BufferSolute(myBuffSol, new BufferingAgent("TES",7.4,true), 0.300));
		myBuffSol.Add(new MetalSolute(myBuffSol, new Metal("K",4,1), 0.015, IonSolute.State.total));
	
		/* 
		 
		above should result in buffer of Fabiato & Fabiato (1979; program 2 testing) and give 
		
		o	[CP]total = 12 mM, added as Na2CP, so [Na2CP]added = 12 mM
		o	[K2CaEGTA]added = 160.971 uM
		o	[Ca]total = 160.987 uM so [CaCl2]added = 16 nM
		o	[K2SrEGTA]added = 99.486 uM
		o	[Sr]total = 100.811 uM so [SrCl2]added = 1.325 uM
		o	[KCl]added = 58.734 mM
		o	[Na2ATP]added = 3.295 mM
		o	[Mg]added = 7.862 mM 
		o	TES 30 mM
		o	15 mM KOH
		o	[K2H2EGTA]total = 5 mM, [K2H2HDTA]total = 5 mM
		o	[NaCl]added = 30.590 mM = 2[Na2CP]+2[Na2ATP]

		*/
	}
}
