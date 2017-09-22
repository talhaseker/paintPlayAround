
import java.io.*;
import java.awt.*;

public class Oval extends BoundedShape{   //draws an oval respect to mouse events
	
	//constructors
	public Oval (Color color, Point corner, int wide, int high){
		super (color, corner, wide, high);
	}

	public Oval (Color strokeColor, Color fillColor, Point corner, int wide, int high){
		super (strokeColor, fillColor, corner, wide, high);
	}
	
	//draw method
	public void draw (Graphics page){
		super.draw(page);
		if (fillColor != null){
			page.setColor (fillColor);
			page.fillOval (upperLeft.x, upperLeft.y, width, height);
		}

		page.setColor (strokeColor);
		page.drawOval (upperLeft.x, upperLeft.y, width, height);
		page.drawOval (upperLeft.x+1, upperLeft.y, width, height);

		if (currentlySelected){
			page.fillRect (upperLeft.x-6, upperLeft.y-6, 4, 4);
			page.fillRect (upperLeft.x+width+2, upperLeft.y-6, 4, 4);
			page.fillRect (upperLeft.x-6, upperLeft.y+height+2, 4, 4);
			page.fillRect (upperLeft.x+width+2, upperLeft.y+height+2, 4, 4);
			page.drawOval (upperLeft.x+width, upperLeft.y+height, 8,8);
		}
	}
	
	// copy information of shape
	public Shape copy (){
		if (fillColor == null)
			return new Oval (new Color (strokeColor.getRGB ()), (Point) (upperLeft.clone ()), width, height);
		else
			return new Oval (new Color (strokeColor.getRGB ()), new Color (fillColor.getRGB ()), (Point) (upperLeft.clone ()), width, height);
	}

	boolean setIntersection(Point clickPoint) {
		return false;
	}
}
