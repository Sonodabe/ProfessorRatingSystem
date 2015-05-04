/**
 * 
 */
package graphics;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import database.SQLDatabaseProxy;

/**
 * @author Doug Blase
 * @author Samantha Wolf
 *
 */
public class ProfessorManager extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8600271417311693707L;
	private JTable dataTable;
	private DefaultTableModel model;
	private ProfessorEditor profFieldsPane;
	private String colNames[] = { "PID", "Name", "Research Area", "Bio",
			"Years Worked" };

	public ProfessorManager() {
		setLayout(new GridLayout(1, 1));

		ArrayList<String> atts = new ArrayList<String>();
		atts.add("PID");
		atts.add("PName");
		atts.add("ResearchArea");
		atts.add("Bio");
		atts.add("YearsWorked");
		ArrayList<String[]> updated = SQLDatabaseProxy
				.select("Professor", atts);
		model = new DefaultTableModel(colNames, 0);
		dataTable = new JTable(model);

		for (int i = 0; i < updated.size(); i++) {
			addRow(updated.get(i));
		}

		dataTable.setName("ProfessorView");
		JScrollPane scrollPane = new JScrollPane(dataTable);
		dataTable.setFillsViewportHeight(true);
		dataTable.setShowGrid(true);
		dataTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		dataTable.setGridColor(Color.BLACK);
		// Object test[][] = new Object[1][1];

		add(scrollPane);
		profFieldsPane = new ProfessorEditor(this);
		add(profFieldsPane);
	}

	private void addRow(String[] row) {

		Vector<Object> rowData = new Vector<Object>();
		rowData.add(row[0]);
		rowData.add(row[1]);
		rowData.add(row[2]);
		rowData.add(row[3]);
		rowData.add(row[4]);
		model.addRow(rowData);

	}

}
