import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;


public class BrushButtonBar extends JToolBar{
	
	public enum brushButtons{
		BRUSH1, BRUSH2, BRUSH3, BRUSH4, BRUSH5
	}
	
	//radio buttons
	private JRadioButton brush1Button, brush2Button, brush3Button, brush4Button, brush5Button;
	private brushButtons currentButton = brushButtons.BRUSH1;
	
	public BrushButtonBar(){
		super(null, JToolBar.VERTICAL);
		
		ClassLoader loader = this.getClass().getClassLoader();
		BrushButtonListener listener = new BrushButtonListener();
		ButtonGroup buttonGroup = new ButtonGroup();
		
		//initialze radio buttons
		brush1Button = new JRadioButton(new ImageIcon(loader.getResource("markerIcon.png")));
		brush1Button.setSelectedIcon(new ImageIcon(loader.getResource("markerIconSelected.png")));
		brush1Button.setToolTipText("Brush 1");
		brush1Button.addActionListener(listener);
		brush1Button.setSelected(true);
		
		brush2Button = new JRadioButton(new ImageIcon(loader.getResource("sprayIcon.png")));
		brush2Button.setSelectedIcon(new ImageIcon(loader.getResource("sprayIconSelected.png")));
		brush2Button.setToolTipText("Brush 2");
		brush2Button.addActionListener(listener);
		
		brush3Button = new JRadioButton(new ImageIcon(loader.getResource("calyIcon.png")));
		brush3Button.setSelectedIcon(new ImageIcon(loader.getResource("calyIconSelected.png")));
		brush3Button.setToolTipText("Brush 3");
		brush3Button.addActionListener(listener);
		
		brush4Button = new JRadioButton(new ImageIcon(loader.getResource("rainbowIcon.png")));
		brush4Button.setSelectedIcon(new ImageIcon(loader.getResource("rainbowIconSelected.png")));
		brush4Button.setToolTipText("Brush 4");
		brush4Button.addActionListener(listener);
		
		brush5Button = new JRadioButton(new ImageIcon(loader.getResource("snakeIcon.png")));
		brush5Button.setSelectedIcon(new ImageIcon(loader.getResource("snakeIconSelected.png")));
		brush5Button.setToolTipText("Brush 5");
		brush5Button.addActionListener(listener);
		
		//add buttons to group
		buttonGroup.add(brush1Button);
		buttonGroup.add(brush2Button);
		buttonGroup.add(brush3Button);
		buttonGroup.add(brush4Button);
		buttonGroup.add(brush5Button);
		
		
		this.add(brush1Button);
		this.add(brush2Button);
		this.add(brush3Button);
		this.add(brush4Button);
		this.add(brush5Button);
		
		this.setFloatable(false);
		this.setVisible(true);
	}
	
	public brushButtons getCurrentButton(){
		return currentButton;
	}
	
	//listener for radio buttons
	private class BrushButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			
			if (source == brush1Button)
				currentButton = brushButtons.BRUSH1;
			else if (source == brush2Button)
				currentButton = brushButtons.BRUSH2;
			else if (source == brush3Button)
				currentButton = brushButtons.BRUSH3;
			else if (source == brush4Button)
				currentButton = brushButtons.BRUSH4;
			else if (source == brush5Button)
				currentButton = brushButtons.BRUSH5;
		}
	}
}