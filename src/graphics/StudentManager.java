/**
 * 
 */
package graphics;

import java.awt.GridLayout;
import javax.swing.*;

/**
 * @author Doug Blase
 *
 */
public class StudentManager extends JPanel {

	// TODO - Implement a table to show listing of students?

	/**
	 * Keeps Eclipse happy.
	 */
	private static final long serialVersionUID = 3040149087941503069L;

	private JTable studentListing;
	private JScrollPane tableViewer;
	private StudentEditor studentFieldsPane;

	/**
	 * 
	 */
	public StudentManager() {
		setLayout(new GridLayout(1, 1));

		if (PRSFrame.JDBC) {
			// Query the database and get the data.
			String colNames[] = { "Name", "Major", "ID" };
			Object test[][] = new Object[1][1];
			studentListing = new JTable(new PRSTableModel(test,
					colNames));
			studentListing.setShowGrid(true);

			tableViewer = new JScrollPane(studentListing);
			studentListing.setFillsViewportHeight(true);

			add(tableViewer);
		}
		studentFieldsPane = new StudentEditor();
		add(studentFieldsPane);
	}

}
