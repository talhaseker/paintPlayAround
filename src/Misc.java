
import java.awt.*;

public class Misc{
	private static final double LINE_TOLERANCE = 5.0;   //determines the amount of tolerance when determining whether two shape intersects or not

	public static boolean checkPointOnLine (Point clickPt, Point lineStart, Point lineEnd){
		double ax = lineStart.getX ();
		double ay = lineStart.getY ();
		double bx = lineEnd.getX ();
		double by = lineEnd.getY ();
		double ptx = clickPt.getX ();
		double pty = clickPt.getY ();

		double slope = (ay - by) / (ax - bx);
		double intercept = ay - (slope * ax);

		if (ax == bx){
			if (Math.abs (ptx - ax) <= LINE_TOLERANCE){
				if (ay < by && pty >= ay && pty <= by)
					return true;
				else if (ay > by && pty <= ay && pty >= by)
					return true;
				else
					return false;
			} else
				return false;

		} else{
			double expectedY = (slope * ptx) + intercept;
			if (Math.abs (pty - expectedY) <= LINE_TOLERANCE)
				return true;
			else
				return false;
		}
	}
}

