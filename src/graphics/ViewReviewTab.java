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
public class ViewReviewTab extends JPanel {
	private JTable classListing; // Course, teacher, university
	private JScrollPane tableViewer;
	private ViewReviewPane vrp;

	public ViewReviewTab() {
		setLayout(new GridLayout(1, 1));

		if (PRSFrame.JDBC) {
			// TODO Query the database and get the data.
			String colNames[] = { "BLAH", "BLAH", "BLAH" };
			Object test[][] = new Object[1][1];
			classListing = new JTable(new PRSTableModel(test,
					colNames));
			classListing.setShowGrid(true);

			tableViewer = new JScrollPane(classListing);
			classListing.setFillsViewportHeight(true);

			add(tableViewer);
		}
		vrp = new ViewReviewPane(this);
		add(vrp);
	}
}
