package org.bio.calculator.calciumbuffer.client;

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
	}
}
