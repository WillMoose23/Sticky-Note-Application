package NoteFrontEnd;

import java.awt.event.*;
import java.time.LocalDateTime;
import DateTimeUtil.*;
import javax.swing.*;
import NoteBackEnd.*;

/*
 * Class: NoteButton
 * NoteButton extends button implements ActionListener
 */

public class NoteButton extends JButton implements ActionListener {
	
	//reference to the Note object the button corresponds with
	private Note note;
	
	//default constructor
	public NoteButton() {
		super();
		this.addActionListener(this);
		this.setVisible(false);
		this.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	//accepts Note and no return, setter method
	public void setNote(Note note) {
		this.note = note;
		this.setText(note.getTitle());
		this.setVisible(true);
	}
	
	//no parameter and no return, removes reference to Note object and hides the object
	public void removeHide() {
		this.note = null;
		this.setVisible(false);
	}

	//accepts ActionEvent and no return, when the button is clicked opens a dialog box to edit the fields for the button
	public void actionPerformed(ActionEvent e) 
	{
		//panel containing the components and layout for the dialog box
		NoteDialogPanel panel = new NoteDialogPanel(note);
		
		//opens a dialog box with fields to allow the user to edit the Note
		int noteEdit = JOptionPane.showConfirmDialog(this, panel);
		
		
		//when Yes is selected changes are updated on the reference to the Note object
		if (noteEdit == JOptionPane.YES_OPTION)  {
			LocalDateTime temp = DateTimeUtil.dateTimeStringParse(panel.getDate(), panel.getTime());	
			this.note.update(panel.getTitle(), panel.getText(), temp);
			if (panel.getCheckBox() == null)  {
                this.note.toggleCompletion(false);
            } 
			else {
                this.note.toggleCompletion(true);
            }
			this.setText(note.getTitle());
		}
	}
}