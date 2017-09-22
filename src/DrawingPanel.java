import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener{
	
	public static final int NONE_SELECTED = -1;
	public enum shapesEnum{
		PEN,LINE,OVAL,RECTANGLE,POLY,BRUSH,ERASER
	}
	
	private Image offScreen;
	private ButtonBar buttonBar;
	private PainterFrame appFrame;
	private ArrayList<ArrayList<Shape>> layerList;
	private ArrayList<ArrayList<Shape>> editLogList;
	private Shape currentShape;
	private Shape selectedShape, copiedShape;
	private Point basePoint, currentPoint, moveShapeBase;
	private shapesEnum lastShape = null;
	private boolean isModified, resizing = false;
	private boolean isFirstTime = true;
	private int lastSelectedIndex = NONE_SELECTED, lastLayer;
	
	public DrawingPanel(ButtonBar buttonBar, PainterFrame appFrame){   //takes parameters from reference holder and creates its objects
		this.buttonBar = buttonBar;
		this.appFrame = appFrame;
		ReferenceHolder.layerIndex = 0;
		ReferenceHolder.layerList = new ArrayList<ArrayList<Shape>>();
		layerList = ReferenceHolder.layerList;
		ReferenceHolder.editLogList = new ArrayList<ArrayList<Shape>>();
		editLogList = ReferenceHolder.editLogList;
		editLogList.add(new ArrayList<Shape>());
		editLogList.add(new ArrayList<Shape>());
		editLogList.add(new ArrayList<Shape>());
		
		layerList.add(new ArrayList<Shape>());
		layerList.add(new ArrayList<Shape>());
		layerList.add(new ArrayList<Shape>());
		
		lastShape = shapesEnum.PEN;
		
		this.setBackground(Color.WHITE);
		this.setMinimumSize(new Dimension(640,360));
		this.setPreferredSize(new Dimension(640,360));
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics page){
		//super.paintComponent(page);
		page.setColor(Color.WHITE);     //background
		page.fillRect(this.getX() - 55, this.getY() - 55, this.getWidth() + 55, this.getHeight());
		//draws by checking which layers are open
		if(ReferenceHolder.isLoneVisible){
			ArrayList<Shape> drawnItemsin = layerList.get(0);
			if(drawnItemsin.size() > 0){
				System.out.println(" lone size " + layerList.get(0).size());
				for(int index = 0; index < drawnItemsin.size(); index++){
					((Shape) drawnItemsin.get(index)).draw(page);
				}
			}
		}
		
		if(ReferenceHolder.isLtwoVisible){
			ArrayList<Shape> drawnItemsin = layerList.get(1);
			if(drawnItemsin.size() > 0){
				for(int index = 0; index < drawnItemsin.size(); index++){
					((Shape) drawnItemsin.get(index)).draw(page);
				}
			}
		}
		
		if(ReferenceHolder.isLthreeVisible){
			ArrayList<Shape> drawnItemsin = layerList.get(2);
			if(drawnItemsin.size() > 0){
				for(int index = 0; index < drawnItemsin.size(); index++){
					((Shape) drawnItemsin.get(index)).draw(page);
				}
			}
		}
	}
	
	public void mousePressed(MouseEvent event) {
		System.out.println("mousePressed called !!!!!!!!");
		ButtonBar.Buttons currentButton = buttonBar.getButton();
		Color strokeColor = buttonBar.getStrokeColor();
		if(isFirstTime){
			isFirstTime = false;
			appFrame.enableUndoMenuItem();
			appFrame.disableRedoButton();
		}
		basePoint = event.getPoint();
		
		//executes respect to which button is selected
		switch (currentButton){
			
			case SELECT:        //executes when user clicks blank
				deselectShape();
				for (ArrayList<Shape> drawnItemsin : layerList){
					for (int index = drawnItemsin.size() - 1 ; index>= 0; index--){
						if(((Shape) drawnItemsin.get(index)).getIntersection(basePoint)){
							((Shape)drawnItemsin.get(index)).setSelected(true);
							selectedShape = (Shape) drawnItemsin.get(index);
							buttonBar.updateColorButton(selectedShape.getStrokeColor());
							lastSelectedIndex = index;
							index = -1;
							appFrame.enableCutMenuItem();
							appFrame.enableCopyMenuItem();
							moveShapeBase = basePoint;
						}
					}
				}
				
				paintComponent(getGraphics());
				lastShape = null;
				break;
				
			case PEN:
				currentShape = new Pen(strokeColor,basePoint,basePoint);
				lastShape = shapesEnum.PEN;
				((ArrayList<Shape>) layerList.get(ReferenceHolder.layerIndex)).add(currentShape);
				appFrame.enableSaveAsMenuItem();
				break;
			case ERASER:
				currentShape = new Eraser(strokeColor,basePoint,basePoint);
				lastShape = shapesEnum.ERASER;
				((ArrayList<Shape>) layerList.get(ReferenceHolder.layerIndex)).add(currentShape);
				appFrame.enableSaveAsMenuItem();	
				break;
			case BRUSH:   //also checks which brush is selected
				BrushButtonBar brushButton = ReferenceHolder.brushButtons;
				lastShape = shapesEnum.BRUSH;
				switch(brushButton.getCurrentButton())
				{
					case BRUSH1:
						currentShape = new Brush (strokeColor, basePoint, basePoint, 1);
						((ArrayList<Shape>) layerList.get(ReferenceHolder.layerIndex)).add(currentShape);
						appFrame.enableSaveAsMenuItem();
						break;
					
					case BRUSH2:
						currentShape = new Brush (strokeColor, basePoint, basePoint, 2);
						((ArrayList<Shape>) layerList.get(ReferenceHolder.layerIndex)).add(currentShape);
						appFrame.enableSaveAsMenuItem();
						break;
					
					case BRUSH3:
						currentShape = new Brush (strokeColor, basePoint, basePoint, 3);
						((ArrayList<Shape>) layerList.get(ReferenceHolder.layerIndex)).add(currentShape);
						appFrame.enableSaveAsMenuItem();
						break;	
						
					case BRUSH4:
						currentShape = new Brush (strokeColor, basePoint, basePoint, 4);
						((ArrayList<Shape>) layerList.get(ReferenceHolder.layerIndex)).add(currentShape);
						appFrame.enableSaveAsMenuItem();
						break;
					
					case BRUSH5:
						currentShape = new Brush (strokeColor, basePoint, basePoint, 5);
						((ArrayList<Shape>) layerList.get(ReferenceHolder.layerIndex)).add(currentShape);
						appFrame.enableSaveAsMenuItem();
						break;
				}	
				break;
			case SHAPES: //also checks which shape is selected
			{
				ShapesButtonBar shapesButton = ReferenceHolder.shapesButtons;
				switch(shapesButton.getCurrentButton())
				{
					case LINE:
						currentShape = new Line (strokeColor, basePoint, basePoint);
            			lastShape = shapesEnum.LINE;
           				layerList.get(ReferenceHolder.layerIndex).add (currentShape);
         			    isModified = true;
          				appFrame.enableSaveAsMenuItem ();
           				break;
           				
           			case OVAL:
						currentShape = new Oval (strokeColor, basePoint, 0, 0);
            			lastShape = shapesEnum.OVAL;
           				layerList.get(ReferenceHolder.layerIndex).add (currentShape);
         			    isModified = true;
          				appFrame.enableSaveAsMenuItem ();
           				break;
           				
           			case POLY:
           				if(lastShape == null){
           					currentShape = new Poly(strokeColor,basePoint.x,basePoint.y);
           					lastShape = shapesEnum.POLY;
           					((Poly) currentShape).updateLastPoint (basePoint.x, basePoint.y);
           					layerList.get(ReferenceHolder.layerIndex).add (currentShape);
           					isModified = true;
          					appFrame.enableSaveAsMenuItem ();
           				}
           				else if(lastShape == shapesEnum.POLY)
           					((Poly) currentShape).updateLastPoint (basePoint.x, basePoint.y);
           				else 
           				{
           					currentShape = new Poly(strokeColor,basePoint.x,basePoint.y);
           					lastShape = shapesEnum.POLY;
           					((Poly) currentShape).updateLastPoint (basePoint.x, basePoint.y);
           					layerList.get(ReferenceHolder.layerIndex).add (currentShape);
           					isModified = true;
          					appFrame.enableSaveAsMenuItem ();
           						
           				}	
           				((Poly) currentShape).addPoint (basePoint.x, basePoint.y);
           				break;
           				
           			case RECTANGLE:
           				currentShape = new Rect (strokeColor, basePoint, 0, 0);
            			lastShape = shapesEnum.RECTANGLE;
           				layerList.get(ReferenceHolder.layerIndex).add (currentShape);
         			    isModified = true;
          				appFrame.enableSaveAsMenuItem ();
				}
			}
				break;
		}
		repaint();
	}
	
	public void mouseReleased(MouseEvent e) {
		if(resizing)
			resizing = false;
	}
	
	public void mouseDragged(MouseEvent event) {   //listener for drawing components that need continuity
		ButtonBar.Buttons currentButton = buttonBar.getButton();
		currentPoint = event.getPoint();
		
		switch(currentButton){
		
			case SELECT:
				if(selectedShape != null){
					if(resizing || selectedShape.onResizePoint(currentPoint)){
						resizing = true;
						selectedShape.reshape((int)(currentPoint.getX() - moveShapeBase.getX()), (int)(currentPoint.getY() - moveShapeBase.getY()));
						moveShapeBase = event.getPoint();
						paintComponent(getGraphics());
						isModified = true;
						appFrame.enableSaveAsMenuItem();
					}else{
						selectedShape.moveShape((int)(currentPoint.getX() - moveShapeBase.getX()), (int)(currentPoint.getY() - moveShapeBase.getY()));
						moveShapeBase = event.getPoint();
						paintComponent(getGraphics());
						isModified = true;
						appFrame.enableSaveAsMenuItem();
					}
				}
				break;
			case PEN:
				((Pen) currentShape).updatePoint(currentPoint);
				break;
			case ERASER:
				((Eraser) currentShape).updatePoint(currentPoint);
				break;
			case BRUSH:
				((Brush) currentShape).updatePoint(currentPoint);
				break;
			case SHAPES:
			{
				ShapesButtonBar shapesButton = ReferenceHolder.shapesButtons;
				switch(shapesButton.getCurrentButton())
				{
					case LINE:
						((Line) currentShape).setEndPoint(currentPoint);
						break;
					
					case OVAL: 
						((Oval) currentShape).setShape(basePoint, currentPoint);
						break;
					
					case POLY:
						((Poly) currentShape).updateLastPoint(currentPoint.x, currentPoint.y);
						break;
					
					case RECTANGLE:
						((Rect) currentShape ).setShape(basePoint, currentPoint);
				}
			}
		}
		repaint();
	}
	
	public void mouseMoved(MouseEvent event) {   //changes type of the cursor
		
		ButtonBar.Buttons currentButton = buttonBar.getButton();
		Point currentPoint = event.getPoint();
		
		if(currentButton == ButtonBar.Buttons.SELECT && selectedShape != null){
			if(selectedShape.onResizePoint(currentPoint)){
				setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
			}else if(selectedShape.getIntersection(currentPoint)){
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}else{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
	
	public boolean undoAction(){    //undoes last move
		int layerIndex = ReferenceHolder.layerIndex;
		int size = layerList.get(layerIndex).size();
		boolean retVal;
		 
		editLogList.get(layerIndex).add(0, layerList.get(layerIndex).get(size-1));
		layerList.get(layerIndex).remove(size - 1);
		 
		if(size <= 1){
			retVal = false;
		}else{
			retVal = true;
		}
		
		repaint();
		return retVal;
	}
	
	public boolean redoAction(){    //redoes last undid move
		int layerIndex = ReferenceHolder.layerIndex;
		int size = editLogList.get(layerIndex).size();
		boolean retVal;
		isFirstTime = true; 
		layerList.get(layerIndex).add(editLogList.get(layerIndex).get(0));
		editLogList.get(layerIndex).remove(0);
		 
		if(size <= 1){
			retVal = false;
		}else{
			retVal = true;
		}
		
		repaint();
		return retVal;
	}
	
	public void deselectShape(){   //deselects shape
		if(lastSelectedIndex != NONE_SELECTED){
			((Shape)((ArrayList<Shape>)layerList.get(ReferenceHolder.layerIndex)).get(lastSelectedIndex)).setSelected(false);
			
			lastSelectedIndex = NONE_SELECTED;
			selectedShape = null;
			appFrame.disableCopyMenuItem();
			appFrame.disableCutMenuItem();
		}
	}
	
	public void save(String fileName){
		offScreen = createImage(this.getWidth(), this.getHeight());
		
		for (int j = 0; j < layerList.size(); j++){
			for (int i = 0; i < layerList.get(j).size(); i++){
				layerList.get(j).get(i).draw(offScreen.getGraphics());
			}
		}
		
		BufferedImage bi = (BufferedImage)offScreen;
		File outputFile = new File(fileName + ".png");
		try {
			ImageIO.write(bi, "png", outputFile);
			System.out.println("Image successfully saved");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("there is something wrong with the saving system");
		}
	}
	
	public void load(String fileName){
		try {
			Image loadedImage = ImageIO.read(new File(fileName));
			System.out.println("Image successfully loaded  " + fileName);
			getGraphics().drawImage(loadedImage, 0, 0, this);
			repaintSelf();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("there is something wrong with the loading system");
		}
	}
	
	public boolean isModified(){
		return isModified;
	}

	public void newDrawing (){
		for(ArrayList<Shape> drawnItems : layerList){
			drawnItems.clear();
		}
		repaint ();
		isModified = false;
		lastSelectedIndex = NONE_SELECTED;
	}

	public void removeSelectedShape(){
		if(lastSelectedIndex != NONE_SELECTED){
			((ArrayList<Shape>)layerList.get(ReferenceHolder.layerIndex)).remove(lastSelectedIndex);
			lastSelectedIndex = NONE_SELECTED;
			selectedShape = null;
			appFrame.disableCutMenuItem();
			appFrame.disableCopyMenuItem();
		}
	}
	
	public void repaintSelf(){
		paintComponent(this.getGraphics());
	}
	
	public void copySelectedShape(){
		if (lastSelectedIndex != NONE_SELECTED){
			copiedShape = ((Shape)((ArrayList<Shape>)layerList.get(ReferenceHolder.layerIndex)).get(lastSelectedIndex)).copy();
		}
	}
	
	public void pasteSelectedShape(){
		if(copiedShape != null){
			Shape obtainedShape = copiedShape.copy();
			obtainedShape.relocatePaste();
			((ArrayList<Shape>)layerList.get(ReferenceHolder.layerIndex)).add(obtainedShape);
			paintComponent(getGraphics());
		}
	}
	
	public Shape getSelectedShape(){
		if (lastSelectedIndex != NONE_SELECTED)
	         return ((Shape)((ArrayList<Shape>)layerList.get(ReferenceHolder.layerIndex)).get(lastSelectedIndex));
	      else
	         return null;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
