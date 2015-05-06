/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import data.Course;
import database.*;
import database.AttributeValue;

/**
 * @author Doug Blase
 *
 */
public class TeachesEditor extends CallRespondSqlEvent {

	private CourseManager parent;
	private JComboBox<String> courseSelector, professorSelector,
			universitySelector;

	private JButton submitTeacherCourse;

	private ArrayList<Object> values;
	private ArrayList<String> attributes;
	private ArrayList<Integer> profIds;
	private ArrayList<Course> availableCourses;

	/**
	 * @param courseManager
	 */
	public TeachesEditor(CourseManager courseManager) {
		parent = courseManager;
		profIds = new ArrayList<Integer>();

		courseSelector = new JComboBox<String>();
		courseSelector.addItemListener(new ItemResponder());
		universitySelector = new JComboBox<String>();

		professorSelector = new JComboBox<String>();
		availableCourses = new ArrayList<Course>();
		courseSelector.setEditable(false);

		professorSelector.setEditable(false);

		submitTeacherCourse = new JButton("Add Instructor for Course");
		submitTeacherCourse.addActionListener(new ButtonResponder());
		setLayout(new GridBagLayout());
		int y = 0;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(new JLabel("Add an instructor for a course:"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Course: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;

		add(courseSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Professor: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;

		add(professorSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("University: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;

		add(universitySelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y;

		add(submitTeacherCourse, gbc);

		addPanel(this);
		updateSelectors();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphics.CallRespondSqlEvent#updateSelectors()
	 */
	@Override
	protected void updateSelectors() {
		initializeLists();
		PRSFrame.updateSelector(courseSelector, "Course",
				"CIdentifier", currentFilters);
		PRSFrame.updateSelector(professorSelector, "Professor",
				"PName");
		updateProfIds("Professor", "PID");
		attributes.add("CIdentifier");
		attributes.add("CName");
		attributes.add("University");
		attributes.add("UniqueId");
		populateArrayList(SQLDatabaseProxy.select("Course",
				attributes, currentFilters));
		// PRSFrame.updateSelector(universitySelector, "Teaches",
		// "University", currentFilters);
	}

	private class ButtonResponder implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			buildAdd();
		}

	}

	private class ItemResponder implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == courseSelector) {
				if (courseSelector.getItemCount() > 0) {
					ArrayList<AttributeValue> courseFilt = new ArrayList<AttributeValue>();
					courseFilt.add(new AttributeValue("CIdentifier",
							courseSelector.getSelectedItem()));
					PRSFrame.updateSelector(universitySelector,
							"Course", "University", courseFilt);
				}
			}
		}
	}

	/**
	 * 
	 */
	protected void buildAdd() {
		initializeLists();
		attributes.add("CNumber");
		attributes.add("PID");
		values.add(availableCourses.get(
				courseSelector.getSelectedIndex()).getUniqueId());
		values.add(profIds.get(professorSelector.getSelectedIndex()));
		if (SQLDatabaseProxy.insert("Teaches", attributes, values)) {
			super.sqlChanged();
		}
	}

	private void initializeLists() {
		values = new ArrayList<Object>();
		attributes = new ArrayList<String>();
	}

	/**
	 * Updates the list of professorIds so that a professor can easily
	 * be assigned to a class by matching the
	 * <code>selectedIndex</code> of <code>professorSelector</code>
	 * with the index in profIds.
	 * 
	 * @param tableName
	 *            The name of the table to pull the information from
	 *            in the database.
	 * @param fieldName
	 *            The the name of the attribute whose value will be
	 *            retrieved.
	 */
	private void updateProfIds(String tableName, String fieldName) {
		ArrayList<String[]> records;

		ArrayList<String> atts = new ArrayList<String>();
		atts.add(fieldName);

		records = SQLDatabaseProxy.select(tableName, atts);

		profIds.clear();

		for (String[] arr : records) {
			profIds.add(Integer.parseInt(arr[0]));
		}
	}

	/*
	 * Populates the back-end ArrayList of the Course data that is
	 * available to be selected by the JCombBox. This allows for
	 * easier use of update and delete statements.
	 * 
	 * @param select The results from the select query on the database
	 */
	private void populateArrayList(ArrayList<String[]> select) {
		availableCourses.clear();
		for (String[] s : select) {
			availableCourses.add(new Course(s));
		}
	}
}
