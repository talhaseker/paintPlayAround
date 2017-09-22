import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;


public class ButtonBar extends JToolBar{
	
	public enum Buttons{
		SELECT,PEN,ERASER,BRUSH,SIZE,COLOR,SHAPES
	}
	
	//radio buttons
	private JRadioButton selectButton, penButton,eraserButton;
	private JRadioButton brushButton, sizeButton, shapesButton;
	private JButton  colorButton;
	private Buttons currentButton;
	private Color currentStrokeColor;
	//private FileButtonBar fileButtons;
	//private EditButtonBar editButtons;
	private BrushButtonBar brushButtons;
	private ShapesButtonBar shapesButtons;
	private SizesButtonBar sizesButtons;
	private DrawingPanel drawingPanel;
	private JPanel painterPanel;
	private JToolBar currentButtonBar;
	
	public ButtonBar(){
		currentButton = Buttons.PEN;
		currentStrokeColor = Color.black;
		
		//add buttons to the reference holder
		this.brushButtons = ReferenceHolder.brushButtons;
		this.shapesButtons = ReferenceHolder.shapesButtons;
		this.sizesButtons = ReferenceHolder.sizesButtons;
		this.painterPanel = ReferenceHolder.painterPanel;
		
		ClassLoader loader = this.getClass().getClassLoader();
		ButtonListener listener = new ButtonListener();
		ButtonGroup buttonGroup = new ButtonGroup();
		
		//initialise buttons and add image to buttons
		selectButton = new JRadioButton(new ImageIcon(loader.getResource("selectIcon.png")));
		selectButton.setSelectedIcon(new ImageIcon(loader.getResource("selectIconSelected.png")));
		selectButton.addActionListener(listener);
		selectButton.setToolTipText("Select Tool");
		
		penButton = new JRadioButton(new ImageIcon(loader.getResource("penIcon.png")));
		penButton.setSelectedIcon(new ImageIcon(loader.getResource("penIconSelected.png")));
		penButton.addActionListener(listener);
		penButton.setToolTipText("Pen Tool");
		penButton.setSelected(true);
		
		eraserButton = new JRadioButton(new ImageIcon(loader.getResource("eraserIcon.png")));
		eraserButton.setSelectedIcon(new ImageIcon(loader.getResource("eraserIconSelected.png")));
		eraserButton.addActionListener(listener);
		eraserButton.setToolTipText("Eraser Tool");
		
		brushButton = new JRadioButton(new ImageIcon(loader.getResource("brushIcon.png")));
		brushButton.setSelectedIcon(new ImageIcon(loader.getResource("brushIconSelected.png")));
		brushButton.addActionListener(listener);
		brushButton.setToolTipText("Brush Tool");
		
		sizeButton = new JRadioButton(new ImageIcon(loader.getResource("sizeIcon.png")));
		sizeButton.setSelectedIcon(new ImageIcon(loader.getResource("sizeIconSelected.png")));
		sizeButton.addActionListener(listener);
		sizeButton.setToolTipText("Sizes");
		
		colorButton = new JButton("Color");
		colorButton.setBackground(Color.BLACK);
		colorButton.setForeground(Color.WHITE);
		colorButton.addActionListener(listener);
		colorButton.setToolTipText("Colors Panel");
		
		shapesButton = new JRadioButton(new ImageIcon(loader.getResource("shapeIcon.png")));
		shapesButton.setSelectedIcon(new ImageIcon(loader.getResource("shapeIconSelected.png")));
		shapesButton.addActionListener(listener);
		shapesButton.setToolTipText("Shapes and Stuff");
		
		//add buttons to group
		buttonGroup.add(selectButton);
		buttonGroup.add(penButton);
		buttonGroup.add(eraserButton);
		buttonGroup.add(brushButton);
		buttonGroup.add(sizeButton);
		buttonGroup.add(shapesButton);
		
		this.add(new JToolBar.Separator());
		this.add(selectButton);
		this.add(penButton);
		this.add(eraserButton);
		this.add(brushButton);
		this.add(shapesButton);
		this.add(new JToolBar.Separator());
		this.add(sizeButton);
		this.add(colorButton);
		this.add(new JToolBar.Separator());
		
		this.setFloatable(false);
		this.setVisible(true);
	}
	
	//accessors and mutators
	public Buttons getButton(){
		return currentButton;
	}
	
	public Color getStrokeColor(){
		return currentStrokeColor;
	}
	
	public void setDrawingPanel(DrawingPanel panel){
		drawingPanel = panel;
	}
	
	public void updateColorButton(Color backgroundColor){
		int rgbValue = backgroundColor.getRed () + backgroundColor.getGreen () + backgroundColor.getBlue ();
		
		if(rgbValue<250){
			colorButton.setForeground(Color.WHITE);
		}else{
			colorButton.setForeground(Color.BLACK);
		}
		
		colorButton.setBackground(backgroundColor);
	}
	
	//listener for buttons which are in main button list
	private class ButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			
			Object source = event.getSource();
			
			//if-else statements determine the type of the button
			if(source == selectButton){        
				currentButton = Buttons.SELECT;
			}else if (source == penButton){
				currentButton = Buttons.PEN;
				drawingPanel.deselectShape();
				drawingPanel.repaintSelf();
			}else if (source == eraserButton){
				currentButton = Buttons.ERASER;
				drawingPanel.deselectShape();
				drawingPanel.repaintSelf();
			}
			
			else if(source == brushButton){
				if (currentButtonBar != null){
					ReferenceHolder.painterPanel.remove(currentButtonBar);
				}
				currentButton = Buttons.BRUSH;
				ReferenceHolder.painterPanel.add(brushButtons, BorderLayout.WEST);
				currentButtonBar = brushButtons;
				ReferenceHolder.painterPanel.revalidate();
				ReferenceHolder.painterPanel.repaint();
				ReferenceHolder.frame.revalidate();
				ReferenceHolder.frame.repaint();
			}else if(source == shapesButton){
				if (currentButtonBar != null){
					ReferenceHolder.painterPanel.remove(currentButtonBar);
				}
				currentButton = Buttons.SHAPES;
				ReferenceHolder.painterPanel.add(shapesButtons, BorderLayout.WEST);
				currentButtonBar = shapesButtons;
				ReferenceHolder.painterPanel.revalidate();
				ReferenceHolder.painterPanel.repaint();
				ReferenceHolder.frame.revalidate();
				ReferenceHolder.frame.repaint();
			}else if(source == sizeButton){
				if (currentButtonBar != null){
					ReferenceHolder.painterPanel.remove(currentButtonBar);
				}
				currentButton = Buttons.SIZE;
				ReferenceHolder.painterPanel.add(sizesButtons, BorderLayout.WEST);
				currentButtonBar = sizesButtons;
				ReferenceHolder.painterPanel.revalidate();
				ReferenceHolder.painterPanel.repaint();
				ReferenceHolder.frame.revalidate();
				ReferenceHolder.frame.repaint();
			}else{
				if (currentButtonBar != null){
					ReferenceHolder.painterPanel.remove(currentButtonBar);
				}
				ReferenceHolder.painterPanel.revalidate();
				ReferenceHolder.painterPanel.repaint();
				ReferenceHolder.frame.revalidate();
				ReferenceHolder.frame.repaint();
			}
			
			if(source == colorButton){
				Color returnedColor = JColorChooser.showDialog((Component) event.getSource(), "Set Color...", Color.BLACK);
				if(returnedColor != null){
					currentStrokeColor = returnedColor;
					int rgbValue = returnedColor.getRed () + returnedColor.getGreen () + returnedColor.getBlue ();
					
					if(rgbValue < 250)
						colorButton.setForeground(Color.WHITE);
					else
						colorButton.setForeground(Color.BLACK);
					
					colorButton.setBackground(currentStrokeColor);
				}
				
				if(drawingPanel.getSelectedShape() != null){
					drawingPanel.getSelectedShape().setStrokeColor(currentStrokeColor);
					drawingPanel.repaintSelf();
				}
			}
		}
		
	}
}
