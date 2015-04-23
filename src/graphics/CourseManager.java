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
public class CourseManager extends JPanel {
	private JTable courseListing;
	private JScrollPane tableViewer;

	private CourseEditor ce;

	public CourseManager() {
		setLayout(new GridLayout(1, 1));

		if (PRSFrame.JDBC) {
			// TODO Query the database and get the data.
			String colNames[] = { "Course ID", "Course Name" };
			Object test[][] = new Object[1][1];
			courseListing = new JTable(new PRSTableModel(test,
					colNames));
			courseListing.setShowGrid(true);

			tableViewer = new JScrollPane(courseListing);
			courseListing.setFillsViewportHeight(true);

			add(tableViewer);
		}
		ce = new CourseEditor(this);
		add(ce);
	}
}
