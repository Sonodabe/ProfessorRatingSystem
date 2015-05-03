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

import database.AttributeValue;
import database.SQLDatabaseProxy;

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

	private JTable dataTable;
	private DefaultTableModel model;
	private StudentEditor studentFieldsPane;
	private String[] columnNames = { "Major", "University", "SID" };
	/**
	 * 
	 */
	public StudentManager() {
		setLayout(new GridLayout(1, 1));

		if (true) {
			// TODO Query the database and get the data.
			ArrayList<String> atts = new ArrayList<String>();

			
			atts.add("Major");
			atts.add("University");
			atts.add("SID");

			ArrayList<String[]> updated = SQLDatabaseProxy.select("Student", atts);
			model = new DefaultTableModel(columnNames, 0);
			

			dataTable = new JTable(model);
			for(int i=0; i<updated.size(); i++){
				addRow(updated.get(i));
			}
			
			dataTable.setName("StudentView");
			JScrollPane scrollPane = new JScrollPane(dataTable);
			dataTable.setFillsViewportHeight(true);
			dataTable.setShowGrid(true);
			dataTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
			dataTable.setGridColor(Color.BLACK);
			//Object test[][] = new Object[1][1];
		
			

			
			add(scrollPane);
		}
		studentFieldsPane = new StudentEditor(this);
		add(studentFieldsPane);
	}

	
	private void addRow(String[] row) {

		Vector<Object> rowData = new Vector<Object>();
		rowData.add(row[0]);
		rowData.add(row[1]);
		rowData.add(row[2]);

		model.addRow(rowData);

	}
}
