package NoteFrontEnd;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import NoteBackEnd.*;
/*
 * NoteWindow extends JFrame
 * Window containing all components for GUI
 */

public class NoteWindow extends JFrame{
	
	//reference to the NotesList
	private NotesList data;
	
	//constructor, accepts NoteList object and creates new NoteWindow object
	public NoteWindow() {
		super();
		this.data = new NotesList();
		data.importNote();
		data.sortNote(new AtoZSort());
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				data.exportNote();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupLayout();
	}
	
	//no parameters and return, sets up the layout of the window
	public void setupLayout() {
		this.setSize(750, 750);
		Container notePane;
		notePane = getContentPane();
		this.setTitle("Note App");
		notePane.setBackground(new Color(41, 41, 41));
		
		this.setLayout(new BorderLayout());
		NoteTabPane tabPane = new NoteTabPane(data);
		notePane.add(tabPane);
	}
}
