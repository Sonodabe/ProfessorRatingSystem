package graphics;

import java.awt.*;
import java.sql.SQLException;
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
	private String columnNamesAdmin[] = { "SID", "Professor", "CID", "Year",
			"Semester", "Engagement", "Fairness", "Difficulty of Work",
			"Ease of Learning", "Teaching Style", "Comments" };

	private String columnNamesGroupByProf[] = { "Professor", "Engagement",
			"Fairness", "Difficulty of Work", "Ease of Learning",
			"Teaching Style" };

	private String columnNamesGroupByProfAndClass[] = { "Professor", "CID",
			"Engagement", "Fairness", "Difficulty of Work", "Ease of Learning",
			"Teaching Style" };

	private String columnNamesGroupByStudent[] = { "SID", "Engagement",
			"Fairness", "Difficulty of Work", "Ease of Learning",
			"Teaching Style" };

	private String columnNamesGroupByCourse[] = { "CID", "University",
			"Engagement", "Fairness", "Difficulty of Work", "Ease of Learning",
			"Teaching Style" };

	private String columnNamesUser[] = { "Professor", "CID", "Year",
			"Semester", "Engagement", "Fairness", "Difficulty of Work",
			"Ease of Learning", "Teaching Style", "Comments" };

	private CommentEditor ce;
	private ArrayList<Review> reviews = new ArrayList<Review>();
	private boolean isAdmin;

	private static final int REGULAR_MODE = 0;
	private static final int GROUP_BY_PROF = 1;
	private static final int GROUP_BY_SID = 2;
	private static final int GROUP_BY_COURSE = 3;
	private static final int GROUP_BY_PROF_AND_COURSE = 4;
	private int mode;

	@SuppressWarnings("serial")
	public ViewReviewTab(boolean admin) {
		mode = REGULAR_MODE;
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
		dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

	/**
	 * Adds a row of data to the table.
	 * 
	 * @param row
	 *            The array of data to be added.
	 */
	private void addRow(String[] row) {
		Vector<Object> rowData = new Vector<Object>();
		for (int i = 0; i < row.length; i++) {
			if (row[i] != null && row[i].equals("0")) {
				row[i] = "N/A";
			}
		}
		for (String s : row) {
			rowData.add(s);
		}
		model.addRow(rowData);

	}

	private class RowResponder implements ListSelectionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax
		 * .swing.event.ListSelectionEvent)
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (mode == REGULAR_MODE) {
				if (dataTable.getSelectedRow() != -1) {
					ce.populateComments((String) dataTable.getModel()
							.getValueAt(
									dataTable.getSelectedRow(),
									isAdmin ? columnNamesAdmin.length - 1
											: columnNamesUser.length - 1));
				} else {
					ce.populateComments("");
				}
			}
		}

	}

	/**
	 * Updates a comment for the selected tuple
	 * 
	 * @param newComment
	 *            The new comment.
	 */
	public void updateComment(String newComment) {
		if (mode == REGULAR_MODE) {
			ArrayList<AttributeValue> atts = new ArrayList<AttributeValue>();
			atts.add(new AttributeValue("Comments", newComment));
			ArrayList<AttributeValue> filters = new ArrayList<AttributeValue>();
			Review r = reviews.get(dataTable.getSelectedRow());
			filters.add(new AttributeValue("SID", r.getSid()));
			filters.add(new AttributeValue("PID", r.getPid()));
			filters.add(new AttributeValue("CID", r.getCid()));
			filters.add(new AttributeValue("Year", r.getYear()));
			filters.add(new AttributeValue("Semester", r.getSemester()));
			try {
				if (SQLDatabaseProxy.update("Review", atts, filters) > 0) {
					sqlChanged();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphics.CallRespondSqlEvent#updateSelectors()
	 */
	@Override
	protected void updateSelectors() {
		clearModel();

		reviews.clear();
		switch (mode) {
		case REGULAR_MODE:
			drawRegularTable();
			break;
		case GROUP_BY_PROF:
			drawGroupByProf();
			break;
		case GROUP_BY_SID:
			drawGroupBySid();
			break;
		case GROUP_BY_COURSE:
			drawGroupByCourse();
			break;
		case GROUP_BY_PROF_AND_COURSE:
			drawGroupByProfCourse();
			break;
		}

	}

	/**
	 * Calls for review data to be grouped by Course.
	 */
	private void drawGroupByCourse() {
		clearModel();
		model.setColumnIdentifiers(columnNamesGroupByCourse);
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
		atts.add("Comments");

		ArrayList<String[]> updated;
		updated = SQLDatabaseProxy
				.select("Review", atts, "CID", currentFilters);

		for (int i = 0; i < updated.size(); i++) {
			reviews.add(new Review(updated.get(i)));
		}
		atts.clear();
		atts.add("CIdentifier");
		atts.add("University");
		for (Review r : reviews) {
			updated.clear();
			ArrayList<AttributeValue> filters = new ArrayList<AttributeValue>();
			filters.add(new AttributeValue("UniqueId", r.getCid()));
			updated = SQLDatabaseProxy.select("Course", atts, filters);
			ArrayList<String> vals = new ArrayList<String>();
			vals.add(updated.get(0)[0]);
			vals.add(updated.get(0)[1]);
			vals.add("" + r.getEngagement());
			vals.add("" + r.getFairness());
			vals.add("" + r.getDifficultyWork());
			vals.add("" + r.getEaseLearning());
			vals.add(convertTeachingStyle(r.getTeachingStyle()));
			String values[] = new String[vals.size()];
			vals.toArray(values);
			addRow(values);
		}
	}

	/**
	 * Calls for review data to be averaged by the Professor and course s/he
	 * teaches.
	 */
	private void drawGroupByProfCourse() {
		clearModel();
		model.setColumnIdentifiers(columnNamesGroupByProfAndClass);
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
		atts.add("Comments");

		ArrayList<String[]> updated;
		updated = SQLDatabaseProxy.select("Review", atts, "PID,CID",
				currentFilters);

		for (int i = 0; i < updated.size(); i++) {
			reviews.add(new Review(updated.get(i)));
		}
		atts.clear();
		atts.add("PName");
		atts.add("CIdentifier");
		for (Review r : reviews) {
			updated.clear();
			ArrayList<AttributeValue> filters = new ArrayList<AttributeValue>();
			filters.add(new AttributeValue("PID", r.getPid()));
			filters.add(new AttributeValue("UniqueId", r.getCid()));
			updated = SQLDatabaseProxy
					.select("Professor,Course", atts, filters);
			ArrayList<String> vals = new ArrayList<String>();
			vals.add(updated.get(0)[0]);
			vals.add(updated.get(0)[1]);
			vals.add("" + r.getEngagement());
			vals.add("" + r.getFairness());
			vals.add("" + r.getDifficultyWork());
			vals.add("" + r.getEaseLearning());
			vals.add(convertTeachingStyle(r.getTeachingStyle()));
			String values[] = new String[vals.size()];
			vals.toArray(values);
			addRow(values);
		}
	}

	/**
	 * Calls for review data grouped by Student ID - so we can see the averages
	 * of how a student evaluates all professors, mainly to see if the reviews
	 * can be trusted or not.
	 */
	private void drawGroupBySid() {
		clearModel();
		model.setColumnIdentifiers(columnNamesGroupByStudent);
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
		atts.add("Comments");

		ArrayList<String[]> updated;
		updated = SQLDatabaseProxy
				.select("Review", atts, "SID", currentFilters);

		for (int i = 0; i < updated.size(); i++) {
			reviews.add(new Review(updated.get(i)));
		}

		atts.clear();
		updated.clear();
		for (Review r : reviews) {
			ArrayList<String> vals = new ArrayList<String>();
			vals.add("" + r.getSid());
			vals.add("" + r.getEngagement());
			vals.add("" + r.getFairness());
			vals.add("" + r.getDifficultyWork());
			vals.add("" + r.getEaseLearning());
			vals.add(convertTeachingStyle(r.getTeachingStyle()));
			String values[] = new String[vals.size()];
			vals.toArray(values);
			addRow(values);
		}
	}

	/**
	 * Calls for review data that is grouped by professor - ie the averages of
	 * their scores are calculated.
	 */
	private void drawGroupByProf() {
		clearModel();
		model.setColumnIdentifiers(columnNamesGroupByProf);
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
		atts.add("Comments");

		ArrayList<String[]> updated;
		updated = SQLDatabaseProxy
				.select("Review", atts, "PID", currentFilters);

		for (int i = 0; i < updated.size(); i++) {
			reviews.add(new Review(updated.get(i)));
		}

		atts.clear();
		updated.clear();
		atts.add("PName");
		for (Review r : reviews) {
			updated.clear();
			ArrayList<AttributeValue> filters = new ArrayList<AttributeValue>();
			filters.add(new AttributeValue("PID", r.getPid()));
			updated = SQLDatabaseProxy.select("Professor", atts, filters);
			ArrayList<String> vals = new ArrayList<String>();

			vals.add(updated.get(0)[0]);
			vals.add("" + r.getEngagement());
			vals.add("" + r.getFairness());
			vals.add("" + r.getDifficultyWork());
			vals.add("" + r.getEaseLearning());
			vals.add(convertTeachingStyle(r.getTeachingStyle()));
			String values[] = new String[vals.size()];
			vals.toArray(values);
			addRow(values);
		}
	}

	/**
	 * @param teachingStyle
	 * @return
	 */
	private String convertTeachingStyle(double teachingStyle) {
		int ts = (int) (teachingStyle + 0.5);
		switch (ts) {
		case 1:
			return "Entirely Lab";
		case 2:
			return "Mostly Lab";
		case 3:
			return "Lab/Lecture";
		case 4:
			return "Mostly Lecture";
		case 5:
			return "Entirely Lecture";
		default:
			return "N/A";
		}
	}

	/**
	 * Generates the regular table view and populates the background data with
	 * appropriate info.
	 */
	private void drawRegularTable() {
		model.setColumnIdentifiers(isAdmin ? columnNamesAdmin : columnNamesUser);
		ArrayList<String> atts = new ArrayList<String>();
		atts.add("Distinct Review.SID");
		atts.add("Review.PID");
		atts.add("CID");
		atts.add("Year");
		atts.add("Semester");
		atts.add("Engagement");
		atts.add("Fairness");
		atts.add("DifficultyWork");
		atts.add("EaseLearning");
		atts.add("TeachingStyle");
		atts.add("Comments");

		ArrayList<String[]> updated;
		updated = SQLDatabaseProxy.select("Review,Course,Professor", atts,
				currentFilters);

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
			updated = SQLDatabaseProxy
					.select("Professor,Course", atts, filters);
			String values[] = new String[model.getColumnCount()];
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
			values[x++] = convertTeachingStyle(r.getTeachingStyle());
			values[x++] = r.getComments();
			addRow(values);
		}
	}

	/**
	 * Clears the data from the table
	 */
	private void clearModel() {
		model.setRowCount(0);
	}

	/**
	 * 
	 */
	public void setRegularMode() {
		mode = REGULAR_MODE;
		updateSelectors();
	}

	/**
	 * 
	 */
	public void setProfessorMode() {
		mode = GROUP_BY_PROF;
		updateSelectors();
	}

	/**
	 * 
	 */
	public void setCourseMode() {
		mode = GROUP_BY_COURSE;
		updateSelectors();
	}

	/**
	 * 
	 */
	public void setStudentMode() {
		mode = GROUP_BY_SID;
		updateSelectors();
	}

	/**
	 * 
	 */
	public void setProfCourseMode() {
		mode = GROUP_BY_PROF_AND_COURSE;
		updateSelectors();
	}

}
