package NoteFrontEnd;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import NoteBackEnd.*;

public class SettingPanel extends JPanel{
	
	private NotesList data;
	
	public SettingPanel(NotesList data) {
		super();
		this.data = data;
		setupLayout();
	}
	
	public void setupLayout() {
		//editing panel properties
		this.setLayout(new GridBagLayout());
		this.setBackground(new Color(41,41,41));
		
		//adding components
		Label title = new Label("TO CLEAR ALL DATA PRESS DELETE");
		title.setFont(new Font(Font.SERIF, Font.BOLD, 24));
		title.setForeground(new Color(125, 137, 148));
		JButton reset = new JButton("Delete");
		reset.setFont(new Font(Font.SERIF, Font.BOLD, 24));
		reset.setBackground(new Color(241, 203, 73));
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resetValue = JOptionPane.showConfirmDialog(null, "Delete data? Data will no longer be recoverable, program will close after reset.");
				if (resetValue == JOptionPane.YES_OPTION) {
					data.clearAll();
					data.exportNote();
					System.exit(0);
				}
			}
		});
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.insets = new Insets(6, 10, 4, 4);
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.fill = GridBagConstraints.BOTH;
		this.add(title, constraints);
		constraints.gridy = 1;
		this.add(reset, constraints);
	}
}
