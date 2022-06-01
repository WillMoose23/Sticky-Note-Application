package NoteFrontEnd;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.*;
import DateTimeUtil.*;
import NoteBackEnd.*;

/*
 * NoteBoard extends JPanel
 * Contains the GUI panel for the list of active notes
 */

public class NoteBoard extends JPanel{
	
	//contains a reference to the NotesList list
	private NotesList data;
	
	//int used for storing the current page number
	private int pageNumber = 0;
	
	//int used for the number of notes on each page
	private int notePerPage = 10;
	
	//label used for display the current page number
	private Label pageNumberLabel;
	
	//used for storing the two sets of button
	private ArrayList<NoteButton> noteBtn = new ArrayList<NoteButton>();
	private ArrayList<JButton> recycleBtn = new ArrayList<JButton>();
	private ArrayList<Note> filteredData = new ArrayList<Note>();
	//takes a reference to NotesList as parameter, constructor
	public NoteBoard(NotesList data) {
		super();
		this.data = data;
		setupLayout();
		this.setVisible(true);
	}
	
	//returns and accepts nothing, used for initial layout setup of the frame
	public void setupLayout() {
		//set panel to use GridBagLayout
		this.setLayout(new GridBagLayout());
		
		//setting panel properties
		this.setBackground(new Color(41, 41, 41));
		this.setBorder(BorderFactory.createEmptyBorder());
		
		//Title Label component
		Label titleLabel = new Label("Notes");
		GridBagConstraints constraintTitle = new GridBagConstraints();
		titleLabel.setBackground(new Color(224, 164, 97));
		titleLabel.setForeground(new Color(102, 102, 102));
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		titleLabel.setAlignment(Label.CENTER);
		constraintTitle.gridx = 0;
		constraintTitle.gridy = 0;
		constraintTitle.gridheight = 1;
		constraintTitle.gridwidth = 4;
		constraintTitle.weightx = 0.2;
		constraintTitle.weighty = 0.5;
		constraintTitle.insets = new Insets(4, 10, 4, 10);
		constraintTitle.anchor = GridBagConstraints.PAGE_START;
		constraintTitle.fill = GridBagConstraints.BOTH;
		this.add(titleLabel, constraintTitle);
		
		//Create New Note Button
		JButton newNote = new JButton("Create New Note");
		newNote.setBackground(new Color(224, 164, 97));
		newNote.setForeground(new Color(102, 102, 102));
		newNote.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		//Adds anonymous method for event handling to create a new note
		newNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//panel containing the components and layout for the dialog box
				NoteDialogPanel dialogPanel = new NoteDialogPanel();
				
				//if the user presses yes on the dialog box, takes the data from the box and creates a new Note
				int noteEdit = JOptionPane.showConfirmDialog(null, dialogPanel);
				if (noteEdit == JOptionPane.YES_OPTION) {
					LocalDateTime temp = DateTimeUtil.dateTimeStringParse(dialogPanel.getDate(), dialogPanel.getTime());
					data.addNote(dialogPanel.getTitle(), dialogPanel.getText(), temp);
					refresh();
				}
			}
		});
		
		//Adding search bar navigation buttons
		JTextField searchBar = new JTextField();
		JButton searchButton = new JButton("Search");
		searchButton.setBackground(new Color(171, 190, 206));
		searchButton.setForeground(new Color(81, 87, 91));
		searchButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		JButton clearhButton = new JButton("Clear");
		clearhButton.setBackground(new Color(171, 190, 206));
		clearhButton.setForeground(new Color(81, 87, 91));
		clearhButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		GridBagConstraints searchConstraints = new GridBagConstraints();
		searchConstraints.gridx = 0;
		searchConstraints.gridy = 1;
		searchConstraints.gridheight = 1;
		searchConstraints.gridwidth = 2;
		searchConstraints.weightx = 1;
		searchConstraints.weighty = 0.5;
		searchConstraints.anchor = GridBagConstraints.LINE_START;
		searchConstraints.fill = GridBagConstraints.BOTH;
		searchConstraints.insets = new Insets(4, 10, 2, 4);
		this.add(searchBar, searchConstraints);
		searchConstraints.gridwidth = 1;
		searchConstraints.gridx = 2;
		searchConstraints.weightx = 0.075;
		searchConstraints.insets = new Insets(4, 4, 2, 4);
		this.add(searchButton, searchConstraints);
		searchConstraints.gridx = 3;
		searchConstraints.insets = new Insets(4, 4, 2, 10);
		this.add(clearhButton, searchConstraints);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filteredData = data.searchNote(searchBar.getText());
				searchRefresh();
				}
		});
		clearhButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchBar.setText("");
				refresh();
			}
		});
		
		// create new note and sort navigation buttons
		String[] sortMethods = {"A to Z", "Z to A", "Newest", "Oldest"};
		JComboBox<String> sortBox = new JComboBox<String>(sortMethods);
		sortBox.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		GridBagConstraints constraintNew = new GridBagConstraints();
		constraintNew.gridx = 0;
		constraintNew.gridy = 2;
		constraintNew.gridheight = 1;
		constraintNew.gridwidth = 2;
		constraintNew.weightx = 1;
		constraintNew.weighty = 0.5;
		constraintNew.anchor = GridBagConstraints.LINE_START;
		constraintNew.fill = GridBagConstraints.BOTH;
		constraintNew.insets = new Insets(2, 10, 6, 4);
		this.add(newNote, constraintNew);
		constraintNew.gridx = 2;
		constraintNew.weightx = 0.15;
		constraintNew.insets = new Insets(2, 4, 6, 10);
		this.add(sortBox, constraintNew);
		sortBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sortBox.getSelectedItem().equals("Z to A")) {	
					data.sortNote(new ZtoASort());
				} else if (sortBox.getSelectedItem().equals("Newest")) {
					data.sortNote(new NewToOldSort());
				} else if (sortBox.getSelectedItem().equals("Oldest")) {
					data.sortNote(new OldToNewSort());
				} else if (sortBox.getSelectedItem().equals("A to Z")){
					data.sortNote(new AtoZSort());
				} else {
					data.sortNote(new AtoZSort());
				}
				refresh();
			}
		});
		
		//buttons for display notes and recycle notes
		ArrayList<JPanel> buttonPanel = new ArrayList<JPanel>();
		ArrayList<JPanel> recycleButtonPanel = new ArrayList<JPanel>(); 
		for (int i = 0; i < notePerPage; i++) {
			noteBtn.add(new NoteButton());
			noteBtn.get(i).setBackground(new Color(254, 249, 254));
			noteBtn.get(i).setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			recycleBtn.add(new JButton("Recycle"));
			recycleBtn.get(i).setBackground(new Color(171, 190, 206));
			recycleBtn.get(i).setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
			recycleBtn.get(i).setVisible(false);
			//adds ActionListener with anonymous method for recycling the note that matches with the button index
			recycleBtn.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int deleteResult = JOptionPane.showConfirmDialog(null, "Recycle this note?");
					if (deleteResult == JOptionPane.YES_OPTION) {
						int buttonIndex = recycleBtn.indexOf(e.getSource()) + pageNumber * notePerPage;
						data.recycleNote(buttonIndex);
						refresh();
					}
				}
			});
			buttonPanel.add(new JPanel());
			buttonPanel.get(i).setLayout(new GridLayout(1,1));
			buttonPanel.get(i).setBackground(new Color(41, 41, 41));
			recycleButtonPanel.add(new JPanel());
			recycleButtonPanel.get(i).setLayout(new GridLayout(1,1));
			recycleButtonPanel.get(i).setBackground(new Color(41, 41, 41));
			GridBagConstraints constraintNoteButton = new GridBagConstraints();
			constraintNoteButton.gridx = 0;
			constraintNoteButton.gridy = i+3;
			constraintNoteButton.gridheight = 1;
			constraintNoteButton.gridwidth = 2;
			constraintNoteButton.weightx = 1;
			constraintNoteButton.weighty = 1;
			constraintNoteButton.fill = GridBagConstraints.BOTH;
			constraintNoteButton.insets = new Insets(2, 10, 2, 4);
			buttonPanel.get(i).add(noteBtn.get(i));
			this.add(buttonPanel.get(i), constraintNoteButton);
			constraintNoteButton.gridx = 2;
			constraintNoteButton.gridwidth = 2;
			constraintNoteButton.weightx = 0.15;
			constraintNoteButton.insets = new Insets(2, 4, 2, 10);
			recycleButtonPanel.get(i).add(recycleBtn.get(i));
			this.add(recycleButtonPanel.get(i), constraintNoteButton);
		}
		
		//forward and previous page buttons
		Button forwardPage = new Button("Forward");
		forwardPage.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		forwardPage.setBackground(new Color(241, 203, 73));
		//adds ActionListener with anonymous method for showing the next page of notes
		forwardPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((pageNumber + 1) * notePerPage < data.size()) {
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
		GridBagConstraints pageConstraint = new GridBagConstraints();
		pageConstraint.gridx = 0;
		pageConstraint.gridy = 3 + notePerPage;
		pageConstraint.gridheight = 1;
		pageConstraint.gridwidth = 1;
		pageConstraint.weightx = 1;
		pageConstraint.weighty = 0.5;
		pageConstraint.insets = new Insets(6, 10, 2, 4);
		pageConstraint.fill = GridBagConstraints.BOTH;
		this.add(previousPage, pageConstraint);
		pageConstraint.insets = new Insets(6, 4, 2, 4);
		pageConstraint.gridx = 1;
		this.add(forwardPage, pageConstraint);
		pageConstraint.gridx = 2;
		pageConstraint.gridwidth = 2;
		pageConstraint.weightx = 0.15;
		pageNumberLabel = new Label();
		pageNumberLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		pageNumberLabel.setForeground(new Color(170, 189, 205));
		pageNumberLabel.setAlignment(Label.CENTER);
		this.add(pageNumberLabel, pageConstraint);
		refresh();
	}
	
	//no parameters and return, resets the page number and updates the button
	public void refresh() {
		pageNumber = 0;
		updateButton();
	}
	
	//returns and accepts void, used for refreshing all the buttons in the panel
	public void updateButton() {
		pageNumberLabel.setText("Page Number: " + String.format("%d", pageNumber + 1));
		for (int i = 0; i < notePerPage; i++) {
			if (notePerPage * pageNumber + i < data.size()) {
				noteBtn.get(i).setNote(data.get(notePerPage*pageNumber+i));
				recycleBtn.get(i).setVisible(true);
			} else {
				noteBtn.get(i).removeHide();
				recycleBtn.get(i).setVisible(false);
			}
		}
		this.revalidate();
	}
	
	//returns and accepts void, used for refreshing what the buttons displays when searching
	public void searchRefresh() {
		pageNumberLabel.setText("Page Number: " + String.format("%d", pageNumber + 1));
		for (int i = 0; i < notePerPage; i++) {
			if (notePerPage * pageNumber + i < filteredData.size()) {
					noteBtn.get(i).setNote(filteredData.get(notePerPage*pageNumber+i));
					recycleBtn.get(i).setVisible(true);
				} else {
					noteBtn.get(i).removeHide();
					recycleBtn.get(i).setVisible(false);
				}
			}
		this.revalidate();
	}
}