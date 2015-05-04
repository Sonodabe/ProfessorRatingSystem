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
public class CourseManager extends JPanel {

	private JTable dataTable;
	private DefaultTableModel model;
	private String colNames[] = { "Course ID", "Course Name", "University" };
	private CourseEditor ce;

	public CourseManager() {
		setLayout(new GridLayout(1, 1));

		ArrayList<String> atts = new ArrayList<String>();

		atts.add("CIdentifier");
		atts.add("CName");
		atts.add("University");

		ArrayList<String[]> updated = SQLDatabaseProxy.select("Course", atts);
		model = new DefaultTableModel(colNames, 0);
		dataTable = new JTable(model);

		for (int i = 0; i < updated.size(); i++) {
			addRow(updated.get(i));
		}

		dataTable.setName("CourseView");
		JScrollPane scrollPane = new JScrollPane(dataTable);
		dataTable.setFillsViewportHeight(true);
		dataTable.setShowGrid(true);
		dataTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		dataTable.setGridColor(Color.BLACK);

		add(scrollPane);

		ce = new CourseEditor(this);
		add(ce);
	}

	private void addRow(String[] row) {

		Vector<Object> rowData = new Vector<Object>();
		rowData.add(row[0]);
		rowData.add(row[1]);
		rowData.add(row[2]);
		model.addRow(rowData);

	}
}
