package graphics;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import data.Review;
import database.*;
import database.AttributeValue;

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

		vrp = new ViewReviewPane(this, isAdmin);
		add(vrp);
		add(scrollPane);
		ce = new CommentEditor(admin, this);
		add(ce);
		addPanel(this);
		updateSelectors();
	}

	private void addRow(String[] row) {
		// TODO FIX SOME STUFF...probably have a number of different
		// models, so that group by doesn't show columns that wouldn't
		// be averaged...
		Vector<Object> rowData = new Vector<Object>();
		for (int i = 0; i < row.length; i++) {
			if (row[i] != null && row[i].equals("0")) {
				row[i] = "N/A";
			}
		}
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
		if (isAdmin && row.length == 11) {
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
						.getValueAt(
								dataTable.getSelectedRow(),
								isAdmin ? columnNamesAdmin.length - 1
										: columnNamesUser.length - 1));
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
		ArrayList<AttributeValue> atts = new ArrayList<AttributeValue>();
		atts.add(new AttributeValue("Comments", trim));
		ArrayList<AttributeValue> filters = new ArrayList<AttributeValue>();
		Review r = reviews.get(dataTable.getSelectedRow());
		filters.add(new AttributeValue("SID", r.getSid()));
		filters.add(new AttributeValue("PID", r.getPid()));
		filters.add(new AttributeValue("CID", r.getCid()));
		filters.add(new AttributeValue("Year", r.getYear()));
		filters.add(new AttributeValue("Semester", r.getSemester()));
		if (SQLDatabaseProxy.update("Review", atts, filters) > 0) {
			sqlChanged();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphics.CallRespondSqlEvent#updateSelectors()
	 */
	@Override
	protected void updateSelectors() {
		model.setRowCount(0);
		reviews.clear();
		ArrayList<String> atts = new ArrayList<String>();
		atts.add("Distinct Review.SID");
		atts.add("Review.PID");
		atts.add("CID");
		atts.add("Year");
		atts.add("Semester");
		atts.add("AVG(Engagement)");
		atts.add("AVG(Fairness)");
		atts.add("AVG(DifficultyWork)");
		atts.add("AVG(EaseLearning)");
		atts.add("AVG(TeachingStyle)");
		// atts.add("Comments");

		ArrayList<String[]> updated;
		// TODO FIX SOME STUFF
		if (currentFilters.isEmpty()) {
			updated = SQLDatabaseProxy.select("Review", atts, "PID",
					null);
		}
		else {
			updated = SQLDatabaseProxy.select(
					"Review,Course,Professor", atts, currentFilters);
		}

		for (int i = 0; i < updated.size(); i++) {
			reviews.add(new Review(updated.get(i)));
		}

		atts.clear();
		updated.clear();
		atts.add("PName");
		atts.add("CIdentifier");
		for (Review r : reviews) {
			updated.clear();
			ArrayList<AttributeValue> filters = new ArrayList<AttributeValue>();
			filters.add(new AttributeValue("PID", r.getPid()));
			filters.add(new AttributeValue("UniqueId", r.getCid()));
			updated = SQLDatabaseProxy.select("Professor,Course",
					atts, filters);
			String values[] = new String[isAdmin ? 11 : 10];
			int x = 0;
			if (isAdmin) {
				values[x++] = "" + r.getSid();
			}
			values[x++] = updated.get(0)[0];
			values[x++] = updated.get(0)[1];
			values[x++] = "" + r.getYear();
			values[x++] = r.getSemester();
			values[x++] = "" + r.getEngagement();
			values[x++] = "" + r.getFairness();
			values[x++] = "" + r.getDifficultyWork();
			values[x++] = "" + r.getEaseLearning();
			String teachingStyleConversion;
			switch (r.getTeachingStyle()) {
			case 1:
				teachingStyleConversion = "Entirely Lab";
				break;
			case 2:
				teachingStyleConversion = "Mostly Lab";
				break;
			case 3:
				teachingStyleConversion = "Lab/Lecture";
				break;
			case 4:
				teachingStyleConversion = "Mostly Lecture";
				break;
			case 5:
				teachingStyleConversion = "Entirely Lecture";
				break;
			default:
				teachingStyleConversion = "N/A";
			}
			values[x++] = teachingStyleConversion;
			values[x++] = r.getComments();
			addRow(values);
		}
	}
}
