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
public class ProfessorManager extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8600271417311693707L;

	private JTable profListing;
	private JScrollPane tableViewer;
	private ProfessorEditor profFieldsPane;
	private String colNames[] = { "ID", "Name", "Research Area",
			"Bio", "Field of Study" };
	public ProfessorManager() {
		setLayout(new GridLayout(1, 1));

		if (PRSFrame.JDBC) {
			// TODO Query the database and get the data.
			
			Object test[][] = new Object[1][1];
			profListing = new JTable(
					new PRSTableModel(test, colNames));
			profListing.setShowGrid(true);

			tableViewer = new JScrollPane(profListing);
			profListing.setFillsViewportHeight(true);

			add(tableViewer);
		}
		profFieldsPane = new ProfessorEditor(this);
		add(profFieldsPane);
	}

}
