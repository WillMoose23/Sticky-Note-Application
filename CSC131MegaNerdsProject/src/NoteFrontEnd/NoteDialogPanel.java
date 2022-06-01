package NoteFrontEnd;

import java.awt.*;
import javax.swing.*;
import NoteBackEnd.*;

/*
 * Class: NoteDialogPanel
 * Extends JPanel
 * Contains formatting and components for dialog box for creating and editing notes
 */

public class NoteDialogPanel extends JPanel {
	private JTextField titleBox;
	private JTextArea textBox;
	private JTextField dateBox;
	private JTextField timeBox;
	private JCheckBox completedBox;
	
	//default constructor, creates panel and components with no default data in fields
	NoteDialogPanel() {
		super();
		setupLayout();
	}
	
	//constructor, Note parameter, creates panel and components with Note data in fields
	NoteDialogPanel(Note note) {
		super();
		setupLayout();
		setupField(note);
	}
	
	//no parameter or return, sets up the layout and add components to the panel
	private void setupLayout() {
		//setting the size of the panel
		this.setPreferredSize(new Dimension(700,500));
		
		//setting the layout manager for the panel
		GridBagLayout dialogLayout = new GridBagLayout();
		this.setLayout(dialogLayout);
		
		//creating and initializing components
		JLabel titleLabel = new JLabel("Title:");
		JLabel textLabel = new JLabel("Text:");
		JLabel dateLabel = new JLabel("Date:");
		JLabel timeLabel  = new JLabel("Time:");
		titleBox = new JTextField();
		textBox = new JTextArea();
		textBox.setLineWrap(true);
		dateBox = new JTextField();	
		timeBox = new JTextField();
		titleBox.setHorizontalAlignment(JTextField.LEFT);
		JScrollPane textBoxPane = new JScrollPane(textBox);
		
		//setting up constraints for panel layout and adding components
		GridBagConstraints constraintDialog = new GridBagConstraints();
		constraintDialog.gridx = 0;
		constraintDialog.gridy = 0;
		constraintDialog.gridheight = 1;
		constraintDialog.gridwidth = 1;
		constraintDialog.weightx = 0.25;
		constraintDialog.weighty = 0.05;
		constraintDialog.fill = GridBagConstraints.BOTH;
		this.add(titleLabel, constraintDialog);
		constraintDialog.gridy = 2;
		this.add(dateLabel, constraintDialog);
		constraintDialog.gridx = 2;
		this.add(timeLabel, constraintDialog);
		constraintDialog.gridx = 0;
		constraintDialog.gridy = 1;
		constraintDialog.weighty = 1;
		this.add(textLabel, constraintDialog);
		constraintDialog.gridx = 1;
		constraintDialog.gridy = 0;
		constraintDialog.gridheight = 1;
		constraintDialog.gridwidth = 3;
		constraintDialog.weighty = 0.05;
		constraintDialog.weightx = 1;
		this.add(titleBox, constraintDialog);
		constraintDialog.gridy = 2;
		constraintDialog.gridwidth = 1;
		this.add(dateBox, constraintDialog);
		constraintDialog.gridx = 3;
		this.add(timeBox, constraintDialog);
		constraintDialog.gridwidth = 3;
		constraintDialog.gridy = 1;
		constraintDialog.gridx = 1;
		constraintDialog.weighty = 1;
		this.add(textBoxPane, constraintDialog);
		
		//formatting labels to tell the user the format for date and time
		Label dateFormat = new Label("Format: YYYY/MM/DD");
		Label timeFormat = new Label("Time: HH:MM:SS");
		GridBagConstraints formatConstraint = new GridBagConstraints();
		formatConstraint.gridx = 1;
		formatConstraint.gridy = 3;
		formatConstraint.fill = GridBagConstraints.BOTH;
		this.add(dateFormat, formatConstraint);
		formatConstraint.gridx = 3;
		this.add(timeFormat, formatConstraint);
	}
	
	//Note parameter, no return, sets the fields of the components to the data in the passed Note and adds the check box for completion
	public void setupField(Note note) {
		//adding the check box 
		JLabel completedLabel = new JLabel("Complete:");
		completedBox = new JCheckBox();
		completedBox.setText("");
		GridBagConstraints constraintCheckBox = new GridBagConstraints();
		constraintCheckBox.fill = GridBagConstraints.BOTH;
		constraintCheckBox.gridx = 0;
		constraintCheckBox.gridy = 4;
		constraintCheckBox.gridheight = 1;
		constraintCheckBox.gridwidth = 2;
		constraintCheckBox.weightx = 1;
		constraintCheckBox.weighty = 0.05;
		this.add(completedLabel, constraintCheckBox);
		constraintCheckBox.gridx = 2;
		this.add(completedBox, constraintCheckBox);
		
		//setting the text in the text boxes
		titleBox.setText(note.getTitle());
		textBox.setText(note.getText());
		completedBox.setSelected(note.getCompleted());
		if (note.getDate() != null) {
			String day = String.format("%d", note.getDate().getYear()) + "/" + String.format("%02d", note.getDate().getMonth().getValue()) + "/" + String.format("%02d", note.getDate().getDayOfMonth());
			String time = String.format("%02d", note.getDate().getHour()) + ":" + String.format("%02d", note.getDate().getMinute()) + ":" + String.format("%02d", note.getDate().getSecond());
			dateBox.setText(day);
			timeBox.setText(time);
		}
	}
	
	//no parameter, returns String for the text in the title box
	public String getTitle() {
		return titleBox.getText();
	}
	
	//no parameter, returns String for the text in the text box
	public String getText() {
		return textBox.getText();
	}
	
	//no parameter, returns String for the text in the date box
	public String getDate() {
		return dateBox.getText();
	}
	
	//no parameter, returns String for the text in the time box
	public String getTime() {
		return timeBox.getText();
	}
	
	//no parameter, returns the selected Object in the checkbox
	public Object getCheckBox() {
		return completedBox.getSelectedObjects();
	}
}
