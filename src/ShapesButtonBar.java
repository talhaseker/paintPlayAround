import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;


public class ShapesButtonBar extends JToolBar{
	
	public static enum shapesButtons{
		LINE, OVAL, POLY, RECTANGLE
	}

	//radio buttons
	private JRadioButton lineButton, ovalButton, polyButton, rectangleButton;
	private shapesButtons currentButton = shapesButtons.LINE;
	
	public ShapesButtonBar(){
		super(null, JToolBar.VERTICAL);
		
		ClassLoader loader = this.getClass().getClassLoader();
		ShapesButtonListener listener = new ShapesButtonListener();
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		//initialise buttons
		lineButton = new JRadioButton(new ImageIcon(loader.getResource("lineIcon.png")));
		lineButton.setSelectedIcon(new ImageIcon(loader.getResource("lineIconSelected.png")));
		lineButton.setToolTipText("Line");
		lineButton.addActionListener(listener);
		lineButton.setSelected(true);
		
		ovalButton = new JRadioButton(new ImageIcon(loader.getResource("ovalIcon.png")));
		ovalButton.setSelectedIcon(new ImageIcon(loader.getResource("ovalIconSelected.png")));
		ovalButton.setToolTipText("Oval");
		ovalButton.addActionListener(listener);
		
	
		polyButton = new JRadioButton(new ImageIcon(loader.getResource("polyIcon.png")));
		polyButton.setSelectedIcon(new ImageIcon(loader.getResource("polyIconSelected.png")));
		polyButton.setToolTipText("Poly");
		polyButton.addActionListener(listener);
		
		rectangleButton = new JRadioButton(new ImageIcon(loader.getResource("rectIcon.png")));
		rectangleButton.setSelectedIcon(new ImageIcon(loader.getResource("rectIconSelected.png")));
		rectangleButton.setToolTipText("Rectangle");
		rectangleButton.addActionListener(listener);
		
		buttonGroup.add(lineButton);
		buttonGroup.add(ovalButton);
		buttonGroup.add(polyButton);
		buttonGroup.add(rectangleButton);
		
		this.add(lineButton);
		this.add(ovalButton);
		this.add(polyButton);
		this.add(rectangleButton);
		
		
		this.setFloatable(false);
		this.setVisible(true);
	}
	
	public shapesButtons getCurrentButton(){
		return currentButton;
	}
	
	//listener for buttons
	private class ShapesButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			
			if(source == lineButton)
				currentButton = shapesButtons.LINE;
			else if (source == ovalButton)
			{
				currentButton = shapesButtons.OVAL;
			}	
			else if(source == polyButton)	
			{
				currentButton = shapesButtons.POLY;
			}
			else if(source == rectangleButton) 
			{
				currentButton = shapesButtons.RECTANGLE;
			}	
			
			
		}
		
	}

}
