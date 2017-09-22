/*
 * author: Diogenes
 * date: 23/11/2015
 */
//main class
public class Painterer {

	public static void main(String[] args) {
		
		SplashScreen splashScreen = new SplashScreen();
		splashScreen.setVisible(true);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		PainterFrame frame = new PainterFrame();
		ReferenceHolder.frame = frame;
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
		
		splashScreen.setVisible(false);
	}
}
