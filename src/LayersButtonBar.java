import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class LayersButtonBar extends JToolBar{  //these buttons determine which layers will be visible
												//and also changes the layer that the user is drawing on
	
	public static enum layerButtons{
		UP, DOWN, LONE, LTWO, LTHREE
	}
	
	private JButton upButton, downButton;
	private JRadioButton loneButton, ltwoButton, lthreeButton;
	private JLabel label;
	private layerButtons currentButton = null;
	
	public LayersButtonBar(){
		super(null, JToolBar.VERTICAL);
		ClassLoader loader = this.getClass().getClassLoader();
		
		LayersButtonListener listener = new LayersButtonListener();
		
		upButton = new JButton("UP");
		upButton.addActionListener(listener);
		
		downButton = new JButton("DOWN");
		downButton.addActionListener(listener);
		
		loneButton = new JRadioButton(new ImageIcon(loader.getResource("FirstLayerIcon.png")));
		loneButton.setSelectedIcon(new ImageIcon(loader.getResource("FirstLayerIconSelected.png")));
		loneButton.setSelected(true);
		loneButton.addActionListener(listener);
		
		ltwoButton = new JRadioButton(new ImageIcon(loader.getResource("SecondLayerIcon.png")));
		ltwoButton.setSelectedIcon(new ImageIcon(loader.getResource("SecondLayerIconSelected.png")));
		ltwoButton.setSelected(true);
		ltwoButton.addActionListener(listener);
		
		lthreeButton = new JRadioButton(new ImageIcon(loader.getResource("ThirdLayerIcon.png")));
		lthreeButton.setSelectedIcon(new ImageIcon(loader.getResource("ThirdLayerIconSelected.png")));
		lthreeButton.setSelected(true);
		lthreeButton.addActionListener(listener);
		
		ReferenceHolder.isLoneVisible = true;
		ReferenceHolder.isLtwoVisible = true;
		ReferenceHolder.isLthreeVisible = true;
		
		label = new JLabel("1");
		
		this.add(upButton);
		this.add(downButton);
		this.add(new JToolBar.Separator());
		this.add(label);
		this.add(new JToolBar.Separator());
		this.add(loneButton);
		this.add(ltwoButton);
		this.add(lthreeButton);
		
		this.setFloatable(false);
		this.setVisible(true);
	}
	
	private class LayersButtonListener implements ActionListener{    //listener for layer buttons

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			
			if(source == downButton){
				if(ReferenceHolder.layerIndex == 0 && ReferenceHolder.isLtwoVisible){
					ReferenceHolder.layerIndex = 1;
				}else if(ReferenceHolder.layerIndex == 0 && ReferenceHolder.isLthreeVisible){
					ReferenceHolder.layerIndex = 2;
				}else if(ReferenceHolder.layerIndex == 1 && ReferenceHolder.isLthreeVisible){
					ReferenceHolder.layerIndex = 2;
				}
				label.setText(String.valueOf(ReferenceHolder.layerIndex + 1));
				
			}else if(source == upButton){
				if(ReferenceHolder.layerIndex == 2 && ReferenceHolder.isLtwoVisible){
					ReferenceHolder.layerIndex = 1;
				}else if(ReferenceHolder.layerIndex == 2 && ReferenceHolder.isLoneVisible){
					ReferenceHolder.layerIndex = 0;
				}else if(ReferenceHolder.layerIndex == 1 && ReferenceHolder.isLoneVisible){
					ReferenceHolder.layerIndex = 0;
				}
				label.setText(String.valueOf(ReferenceHolder.layerIndex + 1));
			}
			
			if(source == loneButton){
				ReferenceHolder.isLoneVisible = loneButton.isSelected();
				ReferenceHolder.drawingPanel.repaintSelf();
			}else if (source == ltwoButton){
				ReferenceHolder.isLtwoVisible = ltwoButton.isSelected();
				ReferenceHolder.drawingPanel.repaintSelf();
			}else if(source == lthreeButton){
				ReferenceHolder.isLthreeVisible = lthreeButton.isSelected();
				ReferenceHolder.drawingPanel.repaintSelf();
			}
		}
	}
}
