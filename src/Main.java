import graphics.PRSFrame;
import java.util.ArrayList;
import javax.swing.*;
import database.*;

/**
 * Implements a driver of the program
 * 
 * @author Brandon Sonoda and Samantha Wolf
 *
 */
public class Main {
	public static void main(String[] args) {
		Object[] options = { "User", "Admin" };
		int n = JOptionPane.showOptionDialog(null,
				"Welcome! Are you a user or administrator?",
				"Welcome!", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options,
				options[0]);
		boolean admin = n == 1 ? true : false;
		PRSFrame frame = new PRSFrame(admin);
		frame.setVisible(true);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Test();
	}

	private static void Test() {
		ArrayList<AttributeValue> atts = new ArrayList<AttributeValue>();
		ArrayList<AttributeValue> filter = new ArrayList<AttributeValue>();

		atts.add(new AttributeValue("Bio",
				"Testing the update call..."));
		filter.add(new AttributeValue("PID", 4));

		int updated = SQLDatabaseProxy.update("Professor", atts,
				filter);
		System.out.printf("%d row(s) updated%n", updated);

	}
}
