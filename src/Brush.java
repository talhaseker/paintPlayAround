/*
 * author: Diogenes
 * date: 23/1/2015
 */
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

public class Brush extends Shape implements Serializable{
	Point firstPoint, secondPoint;
	private ArrayList<Point> pointList;
	private ArrayList<Point> pointList2;
	int key;
	int r;
	
	// constructor
    public Brush(Color c, Point p1, Point p2, int key) {
    	pointList = new ArrayList<Point>();
    	pointList2 = new ArrayList<Point>();
    	pointList.add(p1);
    	strokeColor = c;
		
		firstPoint = p1;
		secondPoint = p2;
		this.key = key;
    	r = 4*ReferenceHolder.size;
    }
    
    @Override
    // draw methods of brushes. key determine type of brush
	public void draw(Graphics page) {
		super.draw(page);
		if(key == 1){
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
		}
		else if(key == 2){
			((Graphics2D) page).setStroke(new BasicStroke(1));
			for(int i = 0; i < pointList2.size(); i++){
				Point p1 = pointList2.get(i);	
				int x = p1.x;
				int y = p1.y;
				page.drawLine(x, y, x, y);
			}
		}
		else if(key == 3){
			for(int i = 0; i < pointList.size()-1; i++){
				Point p1 = pointList.get(i);
				Point p2 = pointList.get(i + 1);
				for(int index = -r/2; index<r/2; index++){
					page.drawLine(p1.x-index,p1.y+index,p2.x-index,p2.y+index);
				}	
			}
		}
		else if(key == 4){
			((Graphics2D) page).setStroke(new BasicStroke(3));
			for(int i = 0; i < pointList.size(); i++){
				Point p1 = pointList.get(i);
				
				for(int index = 0; index < 2*r ;index++)
				{
					int num1 = (int)(Math.random()*(r+1));
	        		int x = num1*2 + p1.x - r; 
					int num = (int)(Math.sqrt(Math.pow(r, 2) - Math.pow(x-p1.x, 2)));
					int y = (int)(Math.random()*(num*2+1)) + p1.y - num;
					Color color = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
					page.setColor(color);
					page.drawLine(x, y, x, y);
				}
			}
		}	
		else if(key == 5){
			for(int i = 0; i < pointList.size()-1; i++){
				Point p1 = pointList.get(i);
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
		}
		
		//check it is selected or not
		if (currentlySelected){
			page.fillRect (firstPoint.x-2, firstPoint.y-2, 4, 4);
			page.fillRect (secondPoint.x-2, secondPoint.y-2, 4, 4);

			page.drawOval (secondPoint.x-4, secondPoint.y-4, 8, 8);
		}
	}
	
	@Override
	//
	boolean setIntersection(Point clickPoint) {
		return false;
	}
	
	@Override
	//
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
		for(Point p : pointList2){
			p.translate(x, y);
		}
	}
	
	@Override
	Shape copy() {
		return new Brush (new Color (strokeColor.getRGB ()), (Point) firstPoint.clone (), (Point) secondPoint.clone (), key);
	}
	
		@Override
	void relocatePaste() {
			int offSet = 10;
			for(Point p : pointList){
				p.translate(offSet, offSet);
			}
			for(Point p : pointList2){
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
		out.writeObject(key);
	}
	
	private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject ();
		strokeColor = (Color) in.readObject ();
		firstPoint = (Point) in.readObject ();
		secondPoint = (Point) in.readObject ();
		key = (int)in.readObject();
	}
	
	public void updatePoint(Point p){
        secondPoint = p;
        pointList.add(secondPoint);
        
        if(key == 2){
        	for(int index = 0; index < 3*r; index++)
			{
				int num1 = (int)(Math.random()*(r+1));
        		int x = num1*2 + p.x - r; 
				int num = (int)(Math.sqrt(Math.pow(r, 2) - Math.pow(x-p.x, 2)));
				int y = (int)(Math.random()*(num*2+1)) + p.y - num;
				pointList2.add(new Point(x, y));
			}
		}
	}
}