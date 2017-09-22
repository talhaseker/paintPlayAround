
import java.io.*;
import java.awt.*;

public class Line extends Shape implements Serializable{   //draws line between two given points
	protected Point firstPoint;                            //these points are accessed from mouse events
	protected Point secondPoint;

	public Line (Color color, Point p1, Point p2){
		strokeColor = color;
		firstPoint = p1;
		secondPoint = p2;
	}

	public void setEndPoint (Point endPoint){
		secondPoint = endPoint;
	}

	public void draw (Graphics page){
		super.draw(page);
		page.setColor (strokeColor);
		page.drawLine (firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);

		if (currentlySelected){
			page.fillRect (firstPoint.x-2, firstPoint.y-2, 4, 4);
			page.fillRect (secondPoint.x-2, secondPoint.y-2, 4, 4);

			page.drawOval (secondPoint.x-4, secondPoint.y-4, 8,8);
		}
	}

	private void writeObject (ObjectOutputStream out) throws IOException{
		out.defaultWriteObject ();
		out.writeObject (strokeColor);
		out.writeObject (firstPoint);
		out.writeObject (secondPoint);
	}

	private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject ();
		strokeColor = (Color) in.readObject ();
		firstPoint = (Point) in.readObject ();
		secondPoint = (Point) in.readObject ();
	}

	public boolean getIntersection (Point clickPoint){
		int clickPointX = (int) clickPoint.getX ();
		int clickPointY = (int) clickPoint.getY ();

		for (int XScanOffset = -2; XScanOffset < 2; XScanOffset++){
			Point tempPoint = new Point (clickPointX + XScanOffset, clickPointY);
			if (Misc.checkPointOnLine (tempPoint, firstPoint, secondPoint))
				return true;
		}

		for (int YScanOffset = -2; YScanOffset < 2; YScanOffset++){
			Point tempPoint = new Point (clickPointX, clickPointY + YScanOffset);
			if (Misc.checkPointOnLine (tempPoint, firstPoint, secondPoint))
				return true;
		}
		return false;
	}

	public void moveShape (int xmove, int ymove){
		firstPoint.translate (xmove, ymove);
		secondPoint.translate (xmove, ymove);
	}

	public Shape copy (){
		return new Line (new Color (strokeColor.getRGB ()), (Point) firstPoint.clone (), (Point) secondPoint.clone ());
	}

	public void relocatePaste (){
		int OFFSET = 10;

		firstPoint.translate (OFFSET, OFFSET);
		secondPoint.translate (OFFSET, OFFSET);
	}
	
	public void reshape (int x, int y){
		secondPoint.translate (x, y);
	}

	public boolean onResizePoint (Point currentPoint){
		double THRESHOLD = 5.0;

		if (secondPoint.distance (currentPoint) <= THRESHOLD)
			return true;
		else
			return false;
	}

	boolean setIntersection(Point clickPoint) {
		return false;
	}

}
