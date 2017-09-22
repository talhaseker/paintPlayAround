/*
 * author: Diogenes
 * date: 23/11/2015
 * this class extends the Shape class so that provide the same basic that provided by Shape class also 
 */
import java.io.*;
import java.awt.*;


abstract class BoundedShape extends Shape implements Serializable{
	protected Point upperLeft;
	protected int width, height;

	//constructors
	public BoundedShape (Color color, Point corner, int wide, int high){
		strokeColor = color;
		upperLeft = corner;
		width = wide;
		height = high;
	}

	public BoundedShape (Color strokeColor, Color fillColor, Point corner, int wide, int high){
		this.strokeColor = strokeColor;
		this.fillColor = fillColor;
		upperLeft = corner;
		width = wide;
		height = high;
	}

	public void setShape (Point firstPt, Point currentPt){
		if (firstPt.x <= currentPt.x)
			if (firstPt.y <= currentPt.y)
				upperLeft = firstPt;
			else
				upperLeft = new Point (firstPt.x, currentPt.y);
		else
			if (firstPt.y <= currentPt.y)
				upperLeft = new Point (currentPt.x, firstPt.y);
			else
				upperLeft = currentPt;

		width = Math.abs (currentPt.x - firstPt.x);
		height = Math.abs (currentPt.y - firstPt.y);
	}
	
	//change the shape's position
	public void moveShape (int xmove, int ymove){
		upperLeft.translate (xmove, ymove);
	}
	
	//check whether the click point is on shape or not
	public boolean getIntersection (Point clickPoint){
		if ((clickPoint.getX () >= upperLeft.x) && (clickPoint.getX () <= upperLeft.x + width) && (clickPoint.getY () >= upperLeft.y) && (clickPoint.getY () <= upperLeft.y + height))
			return true;
		else
			return false;
	}
	
	//write information of shapes while picture was being saved
	private void writeObject (ObjectOutputStream out) throws IOException{
		out.defaultWriteObject ();
		out.writeObject (strokeColor);
		out.writeObject (fillColor);
		out.writeObject (upperLeft);
		out.writeObject (new Integer (width));
		out.writeObject (new Integer (height));
	}
	
	// read information of shapes while we open new picture
	private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject ();
		strokeColor = (Color) in.readObject ();
		fillColor = (Color) in.readObject ();
		upperLeft = (Point) in.readObject ();
		width = ((Integer) in.readObject ()).intValue ();
		height = ((Integer) in.readObject ()).intValue ();
	}

	//paste copy of shape into right down side of original shape instead of same place with original shape
	public void relocatePaste (){
		int OFFSET = 10;

		upperLeft.translate (OFFSET, OFFSET);
	}

	// change size of shape while we change size of shape
	public void reshape (int x, int y){
		width += x;
		height += y;
	}

	public boolean onResizePoint (Point currentPoint){
		double THRESHOLD = 5.0;

		if ((Math.abs ((upperLeft.x+width) - currentPoint.x) <= THRESHOLD) && (Math.abs ((upperLeft.y+height) - currentPoint.y) <= THRESHOLD))
			return true;
		else
			return false;
	}
}
