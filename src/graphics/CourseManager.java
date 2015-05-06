/**
 * 
 */
package graphics;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import database.*;
import database.AttributeValue;

/**
 * @author Doug Blase
 * @author Samantha Wolf
 *
 */
public class CourseManager extends CallRespondSqlEvent {

	private JTable courseTable, teachesTable;
	private DefaultTableModel courseModel, teachesModel;
	private String courseColumnNames[] = { "Course ID",
			"Course Name", "University" };
	private String teachesColumnNames[] = { "Course ID", "Professor",
			"University" };
	private CourseEditor ce;
	private TeachesEditor te;

	@SuppressWarnings("serial")
	public CourseManager() {
		setLayout(new GridBagLayout());

		courseModel = new DefaultTableModel(courseColumnNames, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		teachesModel = new DefaultTableModel(teachesColumnNames, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		courseTable = new JTable(courseModel);

		courseTable.setName("CourseView");
		JScrollPane courseScrollPane = new JScrollPane(courseTable);
		courseTable.setFillsViewportHeight(true);
		courseTable.setShowGrid(true);
		courseTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		courseTable.setGridColor(Color.BLACK);

		teachesTable = new JTable(teachesModel);

		teachesTable.setName("TeachesView");
		JScrollPane teachesScrollPane = new JScrollPane(teachesTable);
		teachesTable.setFillsViewportHeight(true);
		teachesTable.setShowGrid(true);
		teachesTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		teachesTable.setGridColor(Color.BLACK);
		GridBagConstraints gbc = new GridBagConstraints();
		int y = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.insets = new Insets(0, 0, 100, 0);
		add(courseScrollPane, gbc);

		ce = new CourseEditor(this);
		te = new TeachesEditor(this);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y;
		gbc.insets = new Insets(0, 0, 100, 0);
		add(ce, gbc);

		y++;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(teachesScrollPane, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y;
		add(te, gbc);
		super.addPanel(this);
		updateSelectors();
	}

	private void addRowToCourses(String[] row) {

		Vector<Object> rowData = new Vector<Object>();
		rowData.add(row[0]);
		rowData.add(row[1]);
		rowData.add(row[2]);

		courseModel.addRow(rowData);

	}

	private void addRowToTeaches(String[] row) {

		Vector<Object> rowData = new Vector<Object>();
		rowData.add(row[0]);
		rowData.add(row[1]);
		rowData.add(row[2]);

		teachesModel.addRow(rowData);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphics.CallRespondSqlEvent#updateSelectors()
	 */
	@Override
	protected void updateSelectors() {
		courseModel.setRowCount(0);
		teachesModel.setRowCount(0);
		ArrayList<String> atts = new ArrayList<String>();

		atts.add("CIdentifier");
		atts.add("CName");
		atts.add("University");

		ArrayList<String[]> updated = SQLDatabaseProxy.select(
				"Course", atts);
		for (int i = 0; i < updated.size(); i++) {
			addRowToCourses(updated.get(i));
		}
		atts.clear();
		atts.add("CNumber");
		atts.add("PName");
		atts.add("University");
		ArrayList<AttributeValue> professorConditions = new ArrayList<AttributeValue>();
		professorConditions.add(new AttributeValue("Teaches.PID",
				"Professor.PID", AttributeValue.JOIN));
		updated = SQLDatabaseProxy.select("Teaches,Professor", atts,
				professorConditions);
		for (int i = 0; i < updated.size(); i++) {
			addRowToTeaches(updated.get(i));
		}
	}
}
