import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Eraser extends Shape implements Serializable{   //works very similar to pen
	
	Point firstPoint, secondPoint;
	int r = 4*ReferenceHolder.size;
	private ArrayList<Point> pointList;
	
	public Eraser(Color c, Point p1, Point p2){
		
		pointList = new ArrayList<Point>();
		pointList.add(p1);
		
		strokeColor = Color.WHITE;
		
		firstPoint = p1;
		secondPoint = p2;
	}

	@Override
	public void draw(Graphics page) {   //gets size and erases by coloring white
		super.draw(page);
		((Graphics2D) page).setStroke(new BasicStroke(3*ReferenceHolder.size));
		
		for(int i = 0; i < pointList.size()-1; i++){
			Point p1 = new Point( pointList.get(i).x,pointList.get(i).y );
			Point p2 = pointList.get(i + 1);
			page.fillOval(p1.x-r, p1.y-r, 2*r, 2*r);
			page.fillOval(p2.x-r, p2.y-r, 2*r, 2*r);
			int distance = (int)p1.distance(p2);
			while(distance> r)
			{
				p1.x= p1.x+ (p2.x-p1.x)*r/distance;
				p1.y= p1.y+ (p2.y-p1.y)*r/distance;
				distance = distance - r;
				page.fillOval(p1.x-r, p1.y-r, 2*r, 2*r);
			}
			
		}
		
		if (currentlySelected){
			page.fillRect (firstPoint.x-2, firstPoint.y-2, 4, 4);
			page.fillRect (secondPoint.x-2, secondPoint.y-2, 4, 4);

			page.drawOval (secondPoint.x-4, secondPoint.y-4, 8,8);
		}
	}

	boolean setIntersection(Point clickPoint) {
		return false;
	}

	boolean getIntersection(Point clickPoint) {
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


	void moveShape(int x, int y) {
		firstPoint.translate (x, y);
		secondPoint.translate (x, y);
	}


	Shape copy() {
		return new Eraser (new Color (strokeColor.getRGB ()), (Point) firstPoint.clone (), (Point) secondPoint.clone ());
	}


	void relocatePaste() {

	}


	boolean onResizePoint(Point curretnPoint) {

		return false;
	}


	void reshape(int x, int y) {

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
	
	public void updatePoint(Point p){
        secondPoint = p;
        pointList.add(secondPoint);
	}
}
