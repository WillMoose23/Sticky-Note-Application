package NoteFrontEnd;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import NoteBackEnd.*;

/*
 * RecyclePane extends JPanel
 * GUI Panel for the recycled Note objects
 */

public class RecyclePane extends JPanel{
	
	//contains a reference to the NotesList list
	private NotesList data;
	
	//int used for storing the current page number
	private int pageNumber = 0;
	
	//int used for the number of notes on each page
	private int notePerPage = 10;
	
	//label used for display the current page number
	private Label pageNumberLabel;
	
	//used for storing the two sets of buttons
	private ArrayList<JButton> restoreBtn = new ArrayList<JButton>();
	private ArrayList<JButton> deleteBtn = new ArrayList<JButton>();
	
	
	//takes a reference to NotesList as parameter, constructor
	public RecyclePane(NotesList data) {
		super();
		this.data = data;
		setupLayout();
		this.setVisible(true);
	}
	
	//returns and accepts nothing, used for initial setup of the frame
	public void setupLayout() {
		//initializing and setting up a grid layout manager;
		this.setLayout(new GridBagLayout());
		
		//Setting up panel properties
		this.setBackground(new Color(41, 41, 41));
		this.setBorder(BorderFactory.createEmptyBorder());
		
		//Setting up elements
		Label titleLabel = new Label("Archive");
		titleLabel.setBackground(new Color(224, 164, 97));
		titleLabel.setForeground(new Color(102, 102, 102));
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		titleLabel.setAlignment(Label.CENTER);
		GridBagConstraints titleConstraints = new GridBagConstraints();
		titleConstraints.gridx = 0;
		titleConstraints.gridy = 0;
		titleConstraints.gridheight = 1;
		titleConstraints.gridwidth = 3;
		titleConstraints.weightx = 1;
		titleConstraints.weighty = 0.5;
		titleConstraints.insets = new Insets(4, 10, 4, 10);
		titleConstraints.anchor = GridBagConstraints.PAGE_START;
		titleConstraints.fill = GridBagConstraints.BOTH;
		this.add(titleLabel, titleConstraints);
		
		//Setting up and adding navigation components
		Label selectLabel = new Label("Click on a note to restore");
		selectLabel.setBackground(new Color(224, 164, 97));
		selectLabel.setForeground(new Color(102, 102, 102));
		selectLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		selectLabel.setAlignment(Label.CENTER);
		JButton clearNote = new JButton("Clear All Notes");
		clearNote.setBackground(new Color(171, 190, 206));
		clearNote.setForeground(new Color(81, 87, 91));
		clearNote.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		//Adds ActionListener with anonymous method for deleting all notes from the recycle bin
		clearNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if the user presses yes on the dialog box, takes the data from the box and creates a new Note
				int deleteNoteDialog = JOptionPane.showConfirmDialog(null, "Clear archive? All items will no be recoverable.");
				if (deleteNoteDialog == JOptionPane.YES_OPTION) {
					data.deleteAll();
					refresh();
				} else {
					refresh();
				}
			}
		});
		GridBagConstraints navigationConstraints = new GridBagConstraints();
		navigationConstraints.gridx = 0;
		navigationConstraints.gridy = 1;
		navigationConstraints.gridheight = 1;
		navigationConstraints.gridwidth = 2;
		navigationConstraints.weightx = 0.8;
		navigationConstraints.weighty = 0.5;
		navigationConstraints.insets = new Insets(4, 10, 6, 4);
		navigationConstraints.anchor = GridBagConstraints.PAGE_START;
		navigationConstraints.fill = GridBagConstraints.BOTH;
		this.add(selectLabel, navigationConstraints);
		navigationConstraints.insets = new Insets(4, 4, 6, 10);
		navigationConstraints.gridx = 2;
		navigationConstraints.gridwidth = 1;
		navigationConstraints.weightx = 0.2;
		this.add(clearNote, navigationConstraints);

		//instantiating needed buttons
		ArrayList<JPanel> buttonPanel = new ArrayList<JPanel>();
		ArrayList<JPanel> deleteButtonPanel = new ArrayList<JPanel>(); 
		for (int i = 0; i < notePerPage; i++) {
			restoreBtn.add(new JButton());
			restoreBtn.get(i).setBackground(new Color(254, 249, 254));
			restoreBtn.get(i).setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			restoreBtn.get(i).setVisible(false);
			//Adds ActionListener with anonymous method for restoring the note that matches with the corresponding button index
			restoreBtn.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int restoreResult = JOptionPane.showConfirmDialog(null, "Restore this note?");
					if (restoreResult == JOptionPane.YES_OPTION) {
						int buttonIndex = restoreBtn.indexOf(e.getSource())  + pageNumber * notePerPage;
						data.restoreNote(buttonIndex);
						refresh();
					}
				}
			});
			deleteBtn.add(new JButton("Recycle"));
			deleteBtn.get(i).setBackground(new Color(171, 190, 206));
			deleteBtn.get(i).setForeground(new Color(81, 87, 91));
			deleteBtn.get(i).setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			deleteBtn.get(i).setVisible(false);
			//adds ActionListener with anonymous method for recycling the note that matches with the button index
			deleteBtn.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int deleteResult = JOptionPane.showConfirmDialog(null, "Delete this note? Note will no longer be recoverable");
					if (deleteResult == JOptionPane.YES_OPTION) {
						int buttonIndex = deleteBtn.indexOf(e.getSource()) + pageNumber * notePerPage;
						data.delete(buttonIndex);
						refresh();
					}
				}
			});
			buttonPanel.add(new JPanel());
			buttonPanel.get(i).setLayout(new GridLayout(1,1));
			buttonPanel.get(i).setBackground(new Color(41, 41, 41));
			deleteButtonPanel.add(new JPanel());
			deleteButtonPanel.get(i).setLayout(new GridLayout(1,1));
			deleteButtonPanel.get(i).setBackground(new Color(41, 41, 41));
			GridBagConstraints buttonConstraints = new GridBagConstraints();
			buttonConstraints.gridx = 0;
			buttonConstraints.gridy = i+2;
			buttonConstraints.gridheight = 1;
			buttonConstraints.gridwidth = 2;
			buttonConstraints.weightx = 0.8;
			buttonConstraints.weighty = 1;
			buttonConstraints.insets = new Insets(4, 10, 4, 4);
			buttonConstraints.fill = GridBagConstraints.BOTH;
			buttonPanel.get(i).add(restoreBtn.get(i));
			this.add(buttonPanel.get(i), buttonConstraints);
			buttonConstraints.insets = new Insets(4, 4, 4, 10);
			buttonConstraints.gridx = 2;
			buttonConstraints.gridwidth = 1;
			buttonConstraints.weightx = 0.2;
			deleteButtonPanel.get(i).add(deleteBtn.get(i));
			this.add(deleteButtonPanel.get(i), buttonConstraints);
		}
		
		//Adding forward and back components
		Button forwardPage = new Button("Forward");
		forwardPage.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		forwardPage.setBackground(new Color(241, 203, 73));
		//adds ActionListener with anonymous method for showing the next page of notes
		forwardPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((pageNumber + 1) * notePerPage < data.recycleBinSize()) {
					pageNumber++;
					updateButton();
				}
			}
		});
		//adds ActionListener with anonymous method for showing the next page of notes
		Button previousPage = new Button("Back");
		previousPage.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		previousPage.setBackground(new Color(241, 203, 73));
		previousPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pageNumber > 0) {
					pageNumber--;
					updateButton();
				}
			}
		});
		GridBagConstraints pageConstraints = new GridBagConstraints();
		pageConstraints.gridx = 0;
		pageConstraints.gridy = 2 + notePerPage;
		pageConstraints.gridheight = 1;
		pageConstraints.gridwidth = 1;
		pageConstraints.weightx = 0.4;
		pageConstraints.weighty = 1;
		pageConstraints.insets = new Insets(6, 10, 4, 4);
		pageConstraints.anchor = GridBagConstraints.PAGE_START;
		pageConstraints.fill = GridBagConstraints.BOTH;
		this.add(previousPage, pageConstraints);
		pageConstraints.gridx = 1;
		pageConstraints.weightx = 0.4;
		pageConstraints.insets = new Insets(6, 4, 4, 3);
		this.add(forwardPage, pageConstraints);
		pageConstraints.gridx = 2;
		pageConstraints.gridwidth = 1;
		pageConstraints.weightx = 0.2;
		pageNumberLabel = new Label();
		pageNumberLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		pageNumberLabel.setForeground(new Color(170, 189, 205));
		pageNumberLabel.setAlignment(Label.CENTER);
		this.add(pageNumberLabel, pageConstraints);
		refresh();
	}
	
	//returns and accepts void, used for refreshing all the components in the frame
	public void refresh() {
		pageNumber = 0;
		updateButton();
	}
	
	//returns and accepts void, used for refreshing all the buttons in the panel
	public void updateButton() {
		pageNumberLabel.setText("Page Number: " + String.format("%d", pageNumber + 1));
		for (int i = 0; i < notePerPage; i++) {
			if (notePerPage * pageNumber + i < data.recycleBinSize()) {
				restoreBtn.get(i).setText(data.recycleBinGet(notePerPage*pageNumber+i).getTitle());
				restoreBtn.get(i).setVisible(true);
				deleteBtn.get(i).setVisible(true);
			} else {
				restoreBtn.get(i).setVisible(false);
				deleteBtn.get(i).setVisible(false);
			}
		}
	}
}
