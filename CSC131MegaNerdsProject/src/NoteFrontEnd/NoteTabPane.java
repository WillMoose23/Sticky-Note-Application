package NoteFrontEnd;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import NoteBackEnd.*;

/*
 * NoteTabPane extend JTabbedPane implements ChangeListener
 * Contains the panes for the each view
 */

public class NoteTabPane extends JTabbedPane implements ChangeListener{

	private NoteBoard noteBoard;
	private Dashboard dash;
	private RecyclePane archive;
	private SettingPanel settings;
	
	//constructor, accepts reference to NoteList object, creates new instance of NoteTabPane
	public NoteTabPane(NotesList data) {
		//creates and adds new instance of each pane to the instance
		noteBoard = new NoteBoard(data);
		dash = new Dashboard(data);
		archive = new RecyclePane(data);
		settings = new SettingPanel(data);
		this.add("Dashboard", dash);
		this.add("Notes", noteBoard);
		this.add("Archive", archive);
		this.add("Settings", settings);
		this.addChangeListener(this);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(new Color(254, 249, 254));
		this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
	}

	//refreshing the panes within the NoteTabPane instance whenever the tab is switched
	public void stateChanged(ChangeEvent e) {
		if (this.getSelectedComponent() instanceof NoteBoard) {
			noteBoard.refresh();
		} else if (this.getSelectedComponent() instanceof RecyclePane) {
			archive.refresh();
		} else if (this.getSelectedComponent() instanceof Dashboard) {
			dash.refresh();
		}
	}
}
