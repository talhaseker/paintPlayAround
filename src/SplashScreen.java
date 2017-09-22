

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow{
	
	// create opening window
	private final int splashSizeWidth = 1050;
	private final int splashSizeHeight = 1000;
	private final String imageName = "monaLisa.jpg";
	
	public SplashScreen(){
		super();
		this.getContentPane().setLayout(new BorderLayout());
		ClassLoader loader = this.getClass().getClassLoader();
		Image image = (new ImageIcon(loader.getResource(imageName))).getImage();
		this.getContentPane().add(new ImagePanel(image), BorderLayout.CENTER);
		this.getContentPane().add(new LabelPanel(), BorderLayout.EAST);
		
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenDimension.width - splashSizeWidth)/2;
		int y = (screenDimension.height - splashSizeHeight)/2;
		
		this.setBounds(x, y, splashSizeWidth, splashSizeHeight);
		this.setVisible(true);
	}

}
