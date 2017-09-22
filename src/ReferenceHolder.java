import java.util.ArrayList;

import javax.swing.JPanel;

//references for brush, shape and size and also layer

public class ReferenceHolder {
	
	
	public static BrushButtonBar brushButtons = new BrushButtonBar();
	public static ShapesButtonBar shapesButtons = new ShapesButtonBar();
	public static SizesButtonBar sizesButtons = new SizesButtonBar();
	
	public static ButtonBar buttonBar;
	public static DrawingPanel drawingPanel;
	
	public static JPanel painterPanel = new JPanel();
	public static PainterFrame frame;
	
	public static ArrayList<ArrayList<Shape>> layerList;
	public static int layerIndex ;
	public static ArrayList<ArrayList<Shape>> editLogList;
	
	public static boolean isLoneVisible;
	public static boolean isLtwoVisible;
	public static boolean isLthreeVisible;

	public static int size;
}
