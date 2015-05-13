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
public class StudentManager extends CallRespondSqlEvent {

	/**
	 * Keeps Eclipse happy.
	 */
	private static final long serialVersionUID = 3040149087941503069L;

	private JTable dataTable;
	private DefaultTableModel model;
	private StudentEditor studentFieldsPane;
	private String[] columnNames = { "SID", "Major" };

	/**
	 * 
	 */
	@SuppressWarnings("serial")
	public StudentManager() {
		setLayout(new GridBagLayout());

		model = new DefaultTableModel(columnNames, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		dataTable = new JTable(model);

		dataTable.setName("StudentView");
		JScrollPane scrollPane = new JScrollPane(dataTable);
		dataTable.setFillsViewportHeight(true);
		dataTable.setShowGrid(true);
		dataTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		dataTable.setGridColor(Color.BLACK);

		GridBagConstraints gbc = new GridBagConstraints();
		int y = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(scrollPane, gbc);

		studentFieldsPane = new StudentEditor(this);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y;
		add(studentFieldsPane, gbc);

		super.addPanel(this);
		updateSelectors();
	}

	private void addRow(String[] row) {

		Vector<Object> rowData = new Vector<Object>();
		rowData.add(row[0]);
		rowData.add(row[1]);

		model.addRow(rowData);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphics.CallRespondSqlEvent#updateSelectors()
	 */
	@Override
	protected void updateSelectors() {
		ArrayList<String> atts = new ArrayList<String>();

		atts.add("SID");
		atts.add("Major");

		ArrayList<String[]> updated = SQLDatabaseProxy.select(
				"Student", atts, currentFilters);
		model.setRowCount(0);
		for (int i = 0; i < updated.size(); i++) {
			addRow(updated.get(i));
		}
	}

}
