import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;


abstract class Shape {
	protected Color strokeColor, fillColor;
	protected boolean currentlySelected;
	private final int thickness = ReferenceHolder.size;
	
	public Color getStrokeColor(){
		return strokeColor;
	}
	
	public Color getFillColor(){
		return fillColor;
	}
	
	public void setSelected(boolean selected){
		currentlySelected = selected;
	}
	
	public void setStrokeColor(Color strokeColor){
		this.strokeColor = strokeColor;
	}
	
	public void setFillColor(Color fillCollor){
		this.fillColor = fillCollor;
	}
	
	public void removeFillColor(){
		fillColor = null;
	}
	
	public void draw(Graphics p){
		Graphics2D page = (Graphics2D) p;
		page.setColor(strokeColor);
		page.setStroke(new BasicStroke(thickness));
	}
	
	abstract boolean setIntersection(Point clickPoint);
	
	abstract boolean getIntersection (Point clickPoint);
	
	abstract void moveShape(int x, int y);
	
	abstract Shape copy();
	
	abstract void relocatePaste();
	
	abstract boolean onResizePoint(Point curretnPoint);
	
	abstract void reshape(int x, int y);

}
