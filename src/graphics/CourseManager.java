/**
 * 
 */
package graphics;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import database.SQLDatabaseProxy;

/**
 * @author Doug Blase
 * @author Samantha Wolf
 *
 */
public class CourseManager extends CallRespondSqlEvent {

	private JTable dataTable;
	private DefaultTableModel model;
	private String columnNames[] = { "Course ID", "Course Name",
			"University" };
	private CourseEditor ce;

	@SuppressWarnings("serial")
	public CourseManager() {
		setLayout(new GridLayout(1, 1));

		model = new DefaultTableModel(columnNames, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		dataTable = new JTable(model);

		dataTable.setName("CourseView");
		JScrollPane scrollPane = new JScrollPane(dataTable);
		dataTable.setFillsViewportHeight(true);
		dataTable.setShowGrid(true);
		dataTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		dataTable.setGridColor(Color.BLACK);

		add(scrollPane);

		ce = new CourseEditor(this);
		add(ce);
		super.addPanel(this);
		updateSelectors();
	}

	private void addRow(String[] row) {

		Vector<Object> rowData = new Vector<Object>();
		rowData.add(row[0]);
		rowData.add(row[1]);
		rowData.add(row[2]);
		model.addRow(rowData);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphics.CallRespondSqlEvent#updateSelectors()
	 */
	@Override
	protected void updateSelectors() {
		model.setRowCount(0);
		ArrayList<String> atts = new ArrayList<String>();

		atts.add("CIdentifier");
		atts.add("CName");
		atts.add("University");

		ArrayList<String[]> updated = SQLDatabaseProxy.select(
				"Course", atts);
		for (int i = 0; i < updated.size(); i++) {
			addRow(updated.get(i));
		}
	}
}
