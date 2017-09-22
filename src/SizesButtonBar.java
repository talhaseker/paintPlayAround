import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;


public class SizesButtonBar extends JToolBar{
	
	public enum sizeButtons{
		SUPERSLIM, SLIM, NORMAL, LARGE
	}
	
	//radio buttons
	private JRadioButton superSlimButton, slimButton, normalButton, largeButton;
	private sizeButtons currentButton = sizeButtons.SUPERSLIM;
	
	public SizesButtonBar(){
		super(null, JToolBar.VERTICAL);
		
		ClassLoader loader = this.getClass().getClassLoader();
		SizesButtonListener listener = new SizesButtonListener();
		ButtonGroup buttonGroup = new ButtonGroup();
		
		//initialise buttons
		superSlimButton = new JRadioButton(new ImageIcon(loader.getResource("t1.png")));
		superSlimButton.setSelectedIcon(new ImageIcon(loader.getResource("t1Selected.png")));
		superSlimButton.setToolTipText("New File");
		superSlimButton.addActionListener(listener);
		superSlimButton.setSelected(true);
		
		slimButton = new JRadioButton(new ImageIcon(loader.getResource("t2.png")));
		slimButton.setSelectedIcon(new ImageIcon(loader.getResource("t2Selected.png")));
		slimButton.setToolTipText("New File");
		slimButton.addActionListener(listener);
		
		normalButton = new JRadioButton(new ImageIcon(loader.getResource("t3.png")));
		normalButton.setSelectedIcon(new ImageIcon(loader.getResource("t3Selected.png")));
		normalButton.setToolTipText("New File");
		normalButton.addActionListener(listener);
		
		largeButton = new JRadioButton(new ImageIcon(loader.getResource("t4.png")));
		largeButton.setSelectedIcon(new ImageIcon(loader.getResource("t4Selected.png")));
		largeButton.setToolTipText("New File");
		largeButton.addActionListener(listener);
		
		buttonGroup.add(superSlimButton);
		buttonGroup.add(slimButton);
		buttonGroup.add(normalButton);
		buttonGroup.add(largeButton);
		
		this.add(superSlimButton);
		this.add(slimButton);
		this.add(normalButton);
		this.add(largeButton);
		
		this.setFloatable(false);
		this.setVisible(true);
	}
	public sizeButtons getCurrentButton(){
		return currentButton;
	}
	
	//listener for buttons
	private class SizesButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			
			if (source == superSlimButton)
				ReferenceHolder.size = 1;
			else if (source == slimButton)
				ReferenceHolder.size = 3;
			else if (source == normalButton)
				ReferenceHolder.size = 6;
			else if (source == largeButton)
				ReferenceHolder.size = 9;
		}
	}
}
