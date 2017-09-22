
import java.io.*;
import java.awt.*;

public class Rect extends BoundedShape{
	
	public Rect (Color color, Point corner, int wide, int high){
		super (color, corner, wide, high);
	}

	public Rect (Color strokeColor, Color fillColor, Point corner, int wide, int high){
		super (strokeColor, fillColor, corner, wide, high);
	}

	public void draw (Graphics page){
		super.draw(page);
		if (fillColor != null){
			page.setColor (fillColor);
			page.fillRect (upperLeft.x, upperLeft.y, width, height);
		}

		page.setColor (strokeColor);
		page.drawRect (upperLeft.x, upperLeft.y, width, height);
		page.drawRect (upperLeft.x+1, upperLeft.y+1, width-2, height-2);

		if (currentlySelected){
			page.fillRect (upperLeft.x-6, upperLeft.y-6, 4, 4);
			page.fillRect (upperLeft.x+width+2, upperLeft.y-6, 4, 4);
			page.fillRect (upperLeft.x-6, upperLeft.y+height+2, 4, 4);
			page.fillRect (upperLeft.x+width+2, upperLeft.y+height+2, 4, 4);
			page.drawOval (upperLeft.x+width, upperLeft.y+height, 8,8);
		}
	}

	public Shape copy (){
		if (fillColor == null)
			return new Rect (new Color (strokeColor.getRGB ()), (Point) (upperLeft.clone ()), width, height);
		else
			return new Rect (new Color (strokeColor.getRGB ()), new Color (fillColor.getRGB ()), (Point) (upperLeft.clone ()), width, height);
	}

	boolean setIntersection(Point clickPoint) {
		return false;
	}
}
