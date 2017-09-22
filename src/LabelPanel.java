import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class LabelPanel extends JPanel{   //executes opening note and determines some basics properties like font
	
	public LabelPanel(){
		setBackground(Color.cyan);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		JLabel welcome = new JLabel("Welcome to Da Vinci Simulator!!!");
		welcome.setForeground(Color.black);
		welcome.setFont(new Font("Times New Roman", Font.BOLD, 30));
		gbl.setConstraints(welcome, gbc);
		add(welcome);
	}

}
