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

public class Pen extends Shape implements Serializable{
	
	Point firstPoint, secondPoint;
	
	private ArrayList<Point> pointList;
	
	//contructor
	public Pen(Color c, Point p1, Point p2){
		
		pointList = new ArrayList<Point>();
		pointList.add(p1);
		
		strokeColor = c;
		
		firstPoint = p1;
		secondPoint = p2;
	}

	@Override
	//draw method
	public void draw(Graphics page) {
		super.draw(page);
		for(int i = 0; i < pointList.size() - 1 ; i ++){
			Point p1 = pointList.get(i);
			Point p2 = pointList.get(i + 1);
			page.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
		
		if (currentlySelected){
			page.fillRect (firstPoint.x-2, firstPoint.y-2, 4, 4);
			page.fillRect (secondPoint.x-2, secondPoint.y-2, 4, 4);

			page.drawOval (secondPoint.x-4, secondPoint.y-4, 8,8);
		}
	}

	@Override
	boolean setIntersection(Point clickPoint) {
		
		return false;
	}

	@Override
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

	@Override
	void moveShape(int x, int y) {
		for(Point p : pointList){
			p.translate(x, y);
		}
	}

	@Override
	Shape copy() {
		return new Pen (new Color (strokeColor.getRGB ()), (Point) firstPoint.clone (), (Point) secondPoint.clone ());
	}

	@Override
	void relocatePaste() {
		int offSet = 10;
		for(Point p : pointList){
			p.translate(offSet, offSet);
		}
	}

	@Override
	boolean onResizePoint(Point curretnPoint) {
		
		return false;
	}

	@Override
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
	//uptade new point
	public void updatePoint(Point p){
        secondPoint = p;
        pointList.add(secondPoint);
	}
}
