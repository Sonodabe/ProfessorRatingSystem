package graphics;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import data.Review;
import database.SQLDatabaseProxy;

/**
 * @author Doug Blase
 * @author Samantha Wolf
 *
 */
public class ViewReviewTab extends CallRespondSqlEvent {

	private JTable dataTable;
	private DefaultTableModel model;
	private ViewReviewPane vrp;
	private String columnNamesAdmin[] = { "SID", "PID", "CID",
			"Year", "Semester", "Engagement", "Fairness",
			"Difficulty of Work", "Ease of Learning",
			"Teaching Style", "Comments" };

	private String columnNamesUser[] = { "PID", "CID", "Year",
			"Semester", "Engagement", "Fairness",
			"Difficulty of Work", "Ease of Learning",
			"Teaching Style", "Comments" };

	private CommentEditor ce;
	private ArrayList<Review> reviews = new ArrayList<Review>();
	private boolean isAdmin;

	@SuppressWarnings("serial")
	public ViewReviewTab(boolean admin) {
		setLayout(new GridLayout(3, 0));
		reviews = new ArrayList<Review>();
		isAdmin = admin;

		model = new DefaultTableModel(isAdmin ? columnNamesAdmin
				: columnNamesUser, 0) {

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		dataTable = new JTable(model);

		dataTable.setName("ReviewView");
		dataTable.setRowSelectionAllowed(true);
		dataTable.setColumnSelectionAllowed(false);
		dataTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(dataTable);
		dataTable.setFillsViewportHeight(true);
		dataTable.setShowGrid(true);
		dataTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		dataTable.setGridColor(Color.BLACK);
		dataTable.getSelectionModel().addListSelectionListener(
				new RowResponder());

		// add(scrollPane);

		vrp = new ViewReviewPane(this);
		add(vrp);
		add(scrollPane);
		ce = new CommentEditor(admin);
		add(ce);

	}

	private void addRow(String[] row) {

		Vector<Object> rowData = new Vector<Object>();
		rowData.add(row[0]);
		rowData.add(row[1]);
		rowData.add(row[2]);
		rowData.add(row[3]);
		rowData.add(row[4]);
		rowData.add(row[5]);
		rowData.add(row[6]);
		rowData.add(row[7]);
		rowData.add(row[8]);
		rowData.add(row[9]);
		if (isAdmin) {
			rowData.add(row[10]);
		}
		model.addRow(rowData);

	}

	private class RowResponder implements ListSelectionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.swing.event.ListSelectionListener#valueChanged(javax
		 * .swing.event.ListSelectionEvent)
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (dataTable.getSelectedRow() != -1) {
				ce.populateComments((String) dataTable.getModel()
						.getValueAt(dataTable.getSelectedRow(), 10));
			}
			else {
				ce.populateComments("");
			}
		}

	}

	/**
	 * @param trim
	 */
	public void updateComment(String trim) {
		// TODO SOMETHING

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
		atts.add("PID");
		atts.add("CID");
		atts.add("Year");
		atts.add("Semester");
		atts.add("Engagement");
		atts.add("Fairness");
		atts.add("DifficultyWork");
		atts.add("EaseLearning");
		atts.add("TeachingStyle");
		atts.add("Comments");

		ArrayList<String[]> updated = SQLDatabaseProxy.select(
				"Review", atts, currentFilters);

		for (int i = 0; i < updated.size(); i++) {
			reviews.add(new Review(updated.get(i)));
			// addRow(updated.get(i));
		}

	}
}
