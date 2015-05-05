/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
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
public class StudentManager extends CallRespondSqlEvent {

	/**
	 * Keeps Eclipse happy.
	 */
	private static final long serialVersionUID = 3040149087941503069L;

	private JTable dataTable;
	private DefaultTableModel model;
	private StudentEditor studentFieldsPane;
	private String[] columnNames = { "SID", "Major", "University" };
	private JComboBox<String> universityFilter;

	/**
	 * 
	 */
	@SuppressWarnings("serial")
	public StudentManager() {
		setLayout(new GridBagLayout());
		universityFilter = new JComboBox<String>();
		PRSFrame.updateSelector(universityFilter, "University",
				"UName");
		universityFilter.addItem("NONE");
		universityFilter.setSelectedItem("NONE");
		universityFilter.addItemListener(new ItemResponder());
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

		y++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		add(new JLabel("University Filter:"), gbc);

		y++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		add(universityFilter, gbc);

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
		ArrayList<String> atts = new ArrayList<String>();

		atts.add("SID");
		atts.add("Major");
		atts.add("University");

		ArrayList<String[]> updated = SQLDatabaseProxy.select(
				"Student", atts, currentFilters);
		model.setRowCount(0);
		for (int i = 0; i < updated.size(); i++) {
			addRow(updated.get(i));
		}
	}

	private class ItemResponder implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == universityFilter) {
				applyFilters();
			}
		}
	}

	/**
	 * Creates a filter based on the JComboBox(es) in the table view,
	 * and sends it to the form side of this Panel.
	 */
	protected void applyFilters() {
		ArrayList<AttributeValue> filter = new ArrayList<AttributeValue>();
		if (!universityFilter.getSelectedItem().equals("NONE")) {

			filter.add(new AttributeValue("University",
					universityFilter.getSelectedItem()));
			// TODO Consider order by/group by

		}
		this.updateFilters(filter);
		studentFieldsPane.updateFilters(filter);
	}
}
