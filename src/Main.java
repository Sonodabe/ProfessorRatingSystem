import graphics.PRSFrame;
import javax.swing.*;

/**
 * Implements a driver of the program
 * 
 * @author Brandon Sonoda and Samantha Wolf
 *
 */
public class Main {
	public static void main(String[] args) {
		Object[] options = {"User",
                "Admin"};
		int n = JOptionPane.showOptionDialog(null,
			    "Welcome! Are you a user or administrator?",
			    "Welcome!",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[0]);
		boolean admin= n==1 ? true:false;		
		PRSFrame frame = new PRSFrame(admin);
		frame.setVisible(true);
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
