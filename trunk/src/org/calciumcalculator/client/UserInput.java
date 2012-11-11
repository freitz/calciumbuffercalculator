package org.calciumcalculator.client;

import java.util.LinkedList;
import java.util.List;

import org.calciumcalculator.shared.InputParser;
import org.calciumcalculator.shared.LineOfInput;
import org.calciumcalculator.shared.LineOfInput.Tag;
import org.calciumcalculator.shared.SpeciesLibrary;
import org.calciumcalculator.shared.solution.BufferSolution;
import org.calciumcalculator.shared.species.Species;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class UserInput extends Composite implements HasText {
	private List<LineOfInput> lines = new LinkedList<LineOfInput>();

	private static UserInputUiBinder uiBinder = GWT
			.create(UserInputUiBinder.class);
	@UiField Button addButton;
	@UiField ListBox speciesListBox;
	@UiField ListBox addListBox;
	@UiField ListBox tagListBox;
	@UiField DoubleBox valueBox;
	@UiField ListBox linesOfInputListBox;
	@UiField Button createButton;
	@UiField Button calculateButton;
	@UiField Button testLinesButton;
	@UiField TextArea outputTextArea;
	@UiField Button clearLinesButton;
	@UiField Button clearOutputButton;

	interface UserInputUiBinder extends UiBinder<Widget, UserInput> {
	}

	public UserInput() {
		initWidget(uiBinder.createAndBindUi(this));
		addListBox.clear();
		speciesListBox.clear();
		for(String name : SpeciesLibrary.getAllEntries().keySet()) {
			speciesListBox.addItem(name);
		}
		for(LineOfInput.Tag tag : LineOfInput.Tag.values()) {
			tagListBox.addItem(tag.name());
		}
	}

	public UserInput(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}
	
	@UiHandler("addButton")
	void onButtonClick(ClickEvent event) {
		addListBox.addItem(speciesListBox.getItemText(speciesListBox.getSelectedIndex()));
	}
	
	@UiHandler("createButton")
	void onButtonClick_1(ClickEvent event) {
		Tag tag = Tag.fromString(tagListBox.getItemText(tagListBox.getSelectedIndex()));
		Double value = valueBox.getValue();
		Species[] species = new Species[addListBox.getItemCount()];
		for(int i=0; i<addListBox.getItemCount(); ++i) {
			species[i] = SpeciesLibrary.getAllEntries().get(addListBox.getItemText(i));
		}
		addNewLineOfInput(tag, value, species);
	}
	
	private void addNewLineOfInput(Tag tag, Double value, Species[] species) {
		LineOfInput line = new LineOfInput(tag, value, species);
		lines.add(line);
		linesOfInputListBox.addItem(line.toString());
		addListBox.clear();
		valueBox.setText("");
	}
	
	@UiHandler("calculateButton")
	void onButtonClick_2(ClickEvent event) {
		InputParser parser = new InputParser();
		for(LineOfInput line : lines) {
			parser.parse(line);
		}
		BufferSolution solution = parser.getBufferSolution();
		solution.iterate(0.000000000001, 1000, false);
		outputTextArea.setText(solution.toString());
	}
	
	@UiHandler("testLinesButton")
	void onButtonClick_3(ClickEvent event) {
		// equivalent to totalToFree
        
        //user selects "goal: total -> free" as opposed to "goal: free -> total", required field
		addNewLineOfInput(Tag.goal, 3.0, new Species[]{});
        //why is total-to-free goal represented by "2.0"?  only because Fabiato^2 1979 did it via "Program 3"
        
        //user specifies temperature of 22 (units are deg C), required field
		addNewLineOfInput(Tag.degC, 22.0, new Species[]{}); 

        //user specifies pH of 7.21922, required field
		addNewLineOfInput(Tag.pH, 7.21922384743, new Species[]{});
        
        //with that much, program able to create BufferSolution to add things to
        
        //user selects "TES" from buffer options, specifies 30 (all values are assumed units mM), required field
        addNewLineOfInput(Tag.buffer, 30.0, new Species[]{SpeciesLibrary.TES});
        
        //user specifies total value of 15 for Species[] {K, OH} 
        addNewLineOfInput(Tag.total, 15.0, new Species[]{SpeciesLibrary.K}); //H, OH are subsumed in pH specification 
        
        //user specifies total value of 0 for Species[] {H, Cl}
        addNewLineOfInput(Tag.total, 0.0, new Species[]{SpeciesLibrary.Cl}); //H, OH are subsumed in pH specification 
       
        //user specifies total value of 58.734 for Species[] {K, Cl}

        addNewLineOfInput(Tag.total, 0061.827634499905595 , new Species[]{SpeciesLibrary.K, SpeciesLibrary.Cl}); 
        
        //user specifies total value of 12 for Species[] {Na, Na, CP}; hopefully can input in conventional chemical notation, i.e. Na2CP
        addNewLineOfInput(Tag.total, 12.0, new Species[]{SpeciesLibrary.Na, SpeciesLibrary.Na, SpeciesLibrary.CP}); 
        
        //user specifies total value of 5 for Species[] {K, K, H, H, HDTA}, i.e. K2H2HDTA
        addNewLineOfInput(Tag.total, 5.0, new Species[]{SpeciesLibrary.K, SpeciesLibrary.K, SpeciesLibrary.HDTA}); //H, OH are subsumed in pH specification
        
        //user specifies total value of 0.004899189 for Species[] {K, K, H, H, EGTA}, i.e. K2H2EGTA
        addNewLineOfInput(Tag.total, 4.899189, new Species[]{SpeciesLibrary.K, SpeciesLibrary.K, SpeciesLibrary.EGTA}); //H, OH are subsumed in pH specification
        
        //user specifies total value of 0.100811 for Species[] {K, K, Sr, EGTA}, K2SrEGTA
        addNewLineOfInput(Tag.total, 0.100811, new Species[]{SpeciesLibrary.K, SpeciesLibrary.K, SpeciesLibrary.Sr, SpeciesLibrary.EGTA}); 
        
        //user specifies total value of 0.16087 for Species[] {Ca, Cl, Cl}, i.e. CaCl2
        addNewLineOfInput(Tag.total, 0.160987, new Species[]{SpeciesLibrary.Ca, SpeciesLibrary.Cl, SpeciesLibrary.Cl}); 
        
        //user specifies total value of 7.862 for Species[] {Mg, Cl, Cl}, i.e. MgCl2
        addNewLineOfInput(Tag.total, 4.567, new Species[]{SpeciesLibrary.Mg, SpeciesLibrary.Cl, SpeciesLibrary.Cl}); 
        
        //user specifies total value of 3.295 for Species[] {Na, Na, Mg, ATP}, i.e. Na2MgATP
        addNewLineOfInput(Tag.total, 3.295, new Species[]{SpeciesLibrary.Na, SpeciesLibrary.Na, SpeciesLibrary.Mg, SpeciesLibrary.ATP});
	}
	
	@UiHandler("clearLinesButton")
	void onClearLinesButtonClick(ClickEvent event) {
		linesOfInputListBox.clear();
		lines.clear();
	}
	
	@UiHandler("clearOutputButton")
	void onClearOutputButtonClick(ClickEvent event) {
		outputTextArea.setText("");
	}
}
