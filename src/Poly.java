
import java.io.*;
import java.util.*;
import java.awt.*;

public class Poly extends Shape implements Serializable{
	private ArrayList xList = new ArrayList ();
	private ArrayList yList = new ArrayList ();

	//constructors
	public Poly (Color color, int x, int y){
		strokeColor = color;
		xList.add (new Integer (x));
		yList.add (new Integer (y));
	}

	public Poly (Color color, ArrayList x, ArrayList y){
		strokeColor = color;
		xList = x;
		yList = y;
	}

	public void addPoint (int x, int y){
		xList.add (new Integer (x));
		yList.add (new Integer (y));
	}

	public void updateLastPoint (int x, int y){
		xList.remove (xList.size ()-1);
		xList.add (new Integer (x));
		yList.remove (yList.size ()-1);
		yList.add (new Integer (y));
	}

	//draw method 
	public void draw (Graphics page){
		super.draw(page);
		int[] xtemp = new int[xList.size ()];
		int[] ytemp = new int[yList.size ()];

		for (int index = 0; index < xList.size (); index++){
			xtemp[index] = ((Integer) xList.get (index)).intValue ();
			ytemp[index] = ((Integer) yList.get (index)).intValue ();
		}

		page.setColor (strokeColor);
		page.drawPolyline (xtemp, ytemp, xList.size ());

		if (currentlySelected){
			int firstX = ((Integer) xList.get (0)).intValue ();
			int firstY = ((Integer) yList.get (0)).intValue ();
			int lastX = ((Integer) xList.get (xList.size ()-1)).intValue ();
			int lastY = ((Integer) yList.get (yList.size ()-1)).intValue ();
			page.fillRect (firstX-2, firstY-2, 4, 4);
			page.fillRect (lastX-2, lastY-2, 4, 4);

			page.drawOval (lastX-4, lastY-4, 8,8);
		}
	}

	private void writeObject (ObjectOutputStream out) throws IOException{
		out.defaultWriteObject ();
		out.writeObject (strokeColor);
		out.writeObject (xList);
		out.writeObject (yList);
	}

	private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException{
		in.defaultReadObject ();
		strokeColor = (Color) in.readObject ();
		xList = (ArrayList) in.readObject ();
		yList = (ArrayList) in.readObject ();
	}

	public boolean getIntersection (Point clickPoint){
		int[] xtemp = new int[xList.size ()-1];
		int[] ytemp = new int[yList.size ()-1];

		for (int index = 0; index < xList.size ()-1; index++){
			xtemp[index] = ((Integer) xList.get (index)).intValue ();
			ytemp[index] = ((Integer) yList.get (index)).intValue ();
		}

		for (int loopIndex = 1; loopIndex < xList.size ()-1; loopIndex++){
			if (Misc.checkPointOnLine (clickPoint, new Point (xtemp[loopIndex-1], ytemp[loopIndex-1]), new Point (xtemp[loopIndex], ytemp[loopIndex])))
				return true;
		}

		return false;
	}

	public void moveShape (int xmove, int ymove){
		for (int index = 0; index < xList.size (); index++){
			int newXValue = ((Integer) xList.get (index)).intValue () + xmove;
			int newYValue = ((Integer) yList.get (index)).intValue () + ymove;
			xList.set (index, new Integer (newXValue));
			yList.set (index, new Integer (newYValue));
		}
	}

	public Shape copy (){
		ArrayList newXList = new ArrayList ();
		ArrayList newYList = new ArrayList ();

		for (int i=0; i< xList.size (); i++){
			newXList.add (new Integer (((Integer) xList.get (i)).intValue ()));

			newYList.add (new Integer (((Integer) yList.get (i)).intValue ()));
		}
		return new Poly (new Color (strokeColor.getRGB ()), newXList, newYList);
	}

	public void relocatePaste (){
		int OFFSET = 10;

		for (int i=0; i < xList.size (); i++){
			int newXValue = ((Integer) xList.get (i)).intValue () - OFFSET;

			int newYValue = ((Integer) yList.get (i)).intValue () - OFFSET;

			xList.set (i, new Integer (newXValue));
			yList.set (i, new Integer (newYValue));
		}
	}

	public void reshape (int x, int y){
		int index = xList.size () - 1;

		int newXValue = ((Integer) xList.get (index)).intValue () + x;
		int newYValue = ((Integer) yList.get (index)).intValue () + y;

		xList.set (index, new Integer (newXValue));
		yList.set (index, new Integer (newYValue));

		newXValue = ((Integer) xList.get (index-1)).intValue () + x;
		newYValue = ((Integer) yList.get (index-1)).intValue () + y;

		xList.set (index-1, new Integer (newXValue));
		yList.set (index-1, new Integer (newYValue));
	}

	public boolean onResizePoint (Point currentPoint){
		double THRESHOLD = 5.0;

		int lastX = ((Integer) xList.get ( xList.size () - 1)).intValue ();
		int lastY = ((Integer) yList.get ( yList.size () - 1)).intValue ();

		if ((new Point (lastX, lastY)).distance (currentPoint) <= THRESHOLD)
			return true;
		else
			return false;
	}

	boolean setIntersection(Point clickPoint) {
		return false;
	}
}
