package NoteFrontEnd;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import NoteBackEnd.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * NoteTabPane extend JPanel
 * Contains a dashboard view for the user showing upcoming and unfinished notes
 */

public class Dashboard extends JPanel{
	//contains a reference to the main list of notes
	private NotesList data;
	
	//java swing components that need to be accesed by all methods
	private ArrayList<Label> upcomingNoteTitle = new ArrayList<Label>();
	private ArrayList<Label> incompleteNoteTitle = new ArrayList<Label>();;
	private ArrayList<Label> upcomingNoteDate = new ArrayList<Label>();;
	private ArrayList<Label> incompleteNoteDate = new ArrayList<Label>();;
	
	//constructor, accepts a reference the NotesList object
	public Dashboard(NotesList data) {
		this.data = data;
		setupLayout();
	}
	
	//no parameter and return, sets up the components onto the panel
	public void setupLayout() {
		//setting up layout manager
		this.setLayout(new GridBagLayout());

		//changing visual properties of the panel
		this.setBackground(new Color(41, 41, 41));
		this.setBorder(BorderFactory.createEmptyBorder());
		
		//adding the title components the panel
		GridBagConstraints labelConstraints = new GridBagConstraints();
        Label titleLabel = new Label("Dashboard");
		titleLabel.setBackground(new Color(224, 164, 97));
		titleLabel.setForeground(new Color(102, 102, 102));
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        titleLabel.setAlignment(Label.CENTER);
        Label upcomingLabel = new Label("Upcoming");
        upcomingLabel.setBackground(new Color(224, 164, 97));
		upcomingLabel.setForeground(new Color(102, 102, 102));
        upcomingLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        upcomingLabel.setAlignment(Label.CENTER);
        Label incompleteLabel = new Label("Incomplete");
        incompleteLabel.setBackground(new Color(224, 164, 97));
        incompleteLabel.setForeground(new Color(102, 102, 102));
        incompleteLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        incompleteLabel.setAlignment(Label.CENTER);
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
		labelConstraints.gridheight = 1;
		labelConstraints.gridwidth = 2;
		labelConstraints.weightx = 1;
		labelConstraints.weighty = 1;
		labelConstraints.fill = GridBagConstraints.BOTH;
		labelConstraints.insets = new Insets(2, 10, 2, 10);
        this.add(titleLabel, labelConstraints);
        labelConstraints.gridy = 1;
        this.add(upcomingLabel, labelConstraints);
        labelConstraints.gridy = 6;
        this.add(incompleteLabel, labelConstraints);
        
        
        //adding the date and time labels for notes into the panel
        for (int i = 0; i < 4; i++) {
        	upcomingNoteTitle.add(new Label(""));
        	upcomingNoteTitle.get(i).setBackground(new Color(254, 249, 254));
        	upcomingNoteTitle.get(i).setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        	upcomingNoteDate.add(new Label(""));
        	upcomingNoteDate.get(i).setBackground(new Color(254, 249, 254));
        	upcomingNoteDate.get(i).setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
    		GridBagConstraints upcomingConstraints = new GridBagConstraints();
    		upcomingConstraints.gridx = 0;
    		upcomingConstraints.gridy = i + 2;
    		upcomingConstraints.gridheight = 1;
    		upcomingConstraints.gridwidth = 1;
    		upcomingConstraints.weightx = 0.5;
    		upcomingConstraints.weighty = 1;
    		upcomingConstraints.fill = GridBagConstraints.BOTH;
    		upcomingConstraints.insets = new Insets(2, 10, 2, 0);
    		this.add(upcomingNoteDate.get(i), upcomingConstraints);
    		upcomingConstraints.weightx = 1;
    		upcomingConstraints.gridx = 1;
    		upcomingConstraints.insets = new Insets(2, 0, 2, 10);
    		this.add(upcomingNoteTitle.get(i), upcomingConstraints);
    		
    		incompleteNoteTitle.add(new Label(""));
    		incompleteNoteTitle.get(i).setBackground(new Color(254, 249, 254));
    		incompleteNoteTitle.get(i).setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
    		incompleteNoteDate.add(new Label(""));
    		incompleteNoteDate.get(i).setBackground(new Color(254, 249, 254));
    		incompleteNoteDate.get(i).setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
    		GridBagConstraints incompleteConstraints = new GridBagConstraints();
    		incompleteConstraints.gridx = 0;
    		incompleteConstraints.gridy = i + 7;
    		incompleteConstraints.gridheight = 1;
    		incompleteConstraints.gridwidth = 1;
    		incompleteConstraints.weightx = 0.5;
    		incompleteConstraints.weighty = 1;
    		incompleteConstraints.fill = GridBagConstraints.BOTH;
    		incompleteConstraints.insets = new Insets(2, 10, 2, 0);
    		this.add(incompleteNoteDate.get(i), incompleteConstraints);
    		incompleteConstraints.weightx = 1;
    		incompleteConstraints.gridx = 1;
    		incompleteConstraints.insets = new Insets(2, 0, 2, 10);
    		this.add(incompleteNoteTitle.get(i), incompleteConstraints);
        }
        
		refresh();
	}
	
	//no return and parameter, displays the notes to the user
	public void refresh() { 
		//accumulator to keep track of how many notes displayed to the user
		int incompleteAccumulator = 0;
		int upcomingAcculumulator = 0;
		
		//format for converting LocalDateTime to String
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		
		//iterates through the list of notes displaying notes to the user
		for (int i = 0; i < data.size(); i++) {
			//checks if date is null
			if (data.get(i).getDate() != null) {
				//if the current note at the index has a date set after the current date, add the date and title to the labels in the panel
				if (data.get(i).getDate().isAfter(LocalDateTime.now()) && upcomingAcculumulator < 4) {
					upcomingNoteTitle.get(upcomingAcculumulator).setText(data.get(i).getTitle());
					LocalDateTime current = data.get(i).getDate();
					String fixedDate = current.format(formatter);
					upcomingNoteDate.get(upcomingAcculumulator).setText(fixedDate);
					upcomingAcculumulator++;
				}
			}
			//if the current note at the index is incomplete, add the date and title to the labels in the panel
			if (data.get(i).getCompleted() == false && incompleteAccumulator < 4) {
				incompleteNoteTitle.get(incompleteAccumulator).setText(data.get(i).getTitle());
				//checks if date is null
				if (data.get(i).getDate() != null ) {
					LocalDateTime current = data.get(i).getDate();
					String fixedDate = current.format(formatter);
					incompleteNoteDate.get(incompleteAccumulator).setText(fixedDate);
				} else {
					incompleteNoteDate.get(incompleteAccumulator).setText("");
				}
				incompleteAccumulator++;
			}
			//Once 4 incomplete and upcoming notes are found, exit the loop
			if (incompleteAccumulator == 4 && upcomingAcculumulator == 4) {
				break;
			}
		}
		
		//iterates through the number of notes that can be shown and removes the text from labels that do not have a note
		for (int i = 0; i < 4; i++) {
			if (i >= upcomingAcculumulator) {
				upcomingNoteTitle.get(i).setText("");
				upcomingNoteDate.get(i).setText("");
			}
			if (i >= incompleteAccumulator) {
				incompleteNoteTitle.get(i).setText("");
				incompleteNoteDate.get(i).setText("");
			}
		}
	}
}

