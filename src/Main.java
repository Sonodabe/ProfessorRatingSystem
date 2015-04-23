import graphics.PRSFrame;
import javax.swing.JFrame;

/**
 * Implements a driver of the program
 * 
 * @author Brandon Sonoda
 *
 */
public class Main {
	public static void main(String[] args) {
		PRSFrame frame = new PRSFrame(false);
		frame.setVisible(true);
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
