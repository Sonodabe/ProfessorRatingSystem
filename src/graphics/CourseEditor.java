/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import data.Course;
import database.*;
import database.AttributeValue;

/**
 * @author Doug Blase
 *
 */
public class CourseEditor extends CallRespondSqlEvent {

	private JTextField courseName, courseIdentifier;
	private JComboBox<String> courseSelector, operationSelector,
			universitySelector, professorSelector, courseSelector2;

	private JButton submitCourse, submitTeacherCourse;

	private static final int ADD = 0;
	private static final int MODIFY = 1;

	private ArrayList<Object> values;
	private ArrayList<String> attributes;
	private ArrayList<Integer> profIds;
	private ArrayList<Course> availableCourses;

	private CourseManager parent;
	private Course currCourse;

	// TODO ENABLE THE BELOW
	// Professor prof;

	public CourseEditor(CourseManager cm) {
		parent = cm;
		operationSelector = new JComboBox<String>();
		operationSelector.addItem("Add");
		profIds = new ArrayList<Integer>();

		operationSelector.addItem("Modify");

		operationSelector.addItemListener(new ItemResponder());
		courseName = new JTextField();
		courseIdentifier = new JTextField();
		courseSelector = new JComboBox<String>();
		courseSelector2 = new JComboBox<String>();

		universitySelector = new JComboBox<String>();

		professorSelector = new JComboBox<String>();
		availableCourses = new ArrayList<Course>();
		courseSelector.setEditable(false);
		courseSelector.setEnabled(false);
		courseSelector.addItemListener(new ItemResponder());

		courseSelector2.setEditable(false);
		professorSelector.setEditable(false);

		submitCourse = new JButton("Submit");
		submitCourse.addActionListener(new ButtonResponder());
		submitTeacherCourse = new JButton("Add Instructor for Course");
		submitTeacherCourse.addActionListener(new ButtonResponder());
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;

		add(operationSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("Existing Course: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(courseSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Course Identifier: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(courseIdentifier, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("Course Name: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(courseName, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(new JLabel("University: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(universitySelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridheight = 1;

		add(submitCourse, gbc);

		super.addPanel(this);
		this.updateSelectors();
	}

	private class ItemResponder implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == operationSelector) {
				switch (operationSelector.getSelectedIndex()) {
				case ADD:
					courseSelector.setEnabled(false);
					clearTextFields();
					break;
				default:
					courseSelector.setEnabled(true);
					populateFields();
					break;
				}
			}
			else if (e.getSource() == professorSelector) {
				// TODO get Professor data
			}
			else {
				if (courseSelector.isEnabled()) {
					if (courseSelector.getItemCount() > 0) {
						populateFields();
					}

				}
			}
		}
	}

	private class ButtonResponder implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == submitCourse) {
				switch (operationSelector.getSelectedIndex()) {
				case ADD:
					buildAdd();
					break;
				case MODIFY:
					buildModify();
					break;
				}
			}
		}

	}

	private void initializeLists() {
		values = new ArrayList<Object>();
		attributes = new ArrayList<String>();
	}

	/**
	 * 
	 */
	protected void buildModify() {
		ArrayList<AttributeValue> info = new ArrayList<AttributeValue>();
		ArrayList<AttributeValue> filter = new ArrayList<AttributeValue>();
		String cidentifer = courseIdentifier.getText().trim();
		info.add(new AttributeValue("CIdentifier", cidentifer
				.isEmpty() ? null : cidentifer));
		String cname = courseName.getText().trim();
		info.add(new AttributeValue("CName", cname.isEmpty() ? null
				: cname));
		info.add(new AttributeValue("University", universitySelector
				.getSelectedItem()));

		filter.add(new AttributeValue("CIdentifier", availableCourses
				.get(courseSelector.getSelectedIndex())
				.getCIdentifier()));
		filter.add(new AttributeValue("University", availableCourses
				.get(courseSelector.getSelectedIndex())
				.getUniversity()));

		try {
			SQLDatabaseProxy.update("Course", info, filter);
			operationSelector.setSelectedIndex(ADD);
			sqlChanged();

		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}

	/**
	 * Populates the text fields with the data from the currently
	 * selected course.
	 */
	protected void populateFields() {
		currCourse = availableCourses.get(courseSelector
				.getSelectedIndex());
		courseName.setText(currCourse.getCName());
		courseIdentifier.setText(currCourse.getCIdentifier());
		universitySelector
				.setSelectedItem(currCourse.getUniversity());
	}

	/**
	 * @return
	 */
	protected void buildAdd() {
		initializeLists();
		attributes.add("CName");
		if (!courseName.getText().trim().isEmpty()) {

			values.add(courseName.getText());
		}
		else {
			values.add(null);
		}
		attributes.add("CIdentifier");

		if (!courseIdentifier.getText().trim().isEmpty()) {

			values.add(courseIdentifier.getText());
		}
		else {
			values.add(null);
		}
		attributes.add("University");
		values.add(universitySelector.getItemAt(universitySelector
				.getSelectedIndex()));

		try {
			SQLDatabaseProxy.insert("Course", attributes, values);
			super.sqlChanged();
			clearTextFields();
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}

	/**
	 * 
	 */
	private void clearTextFields() {
		courseIdentifier.setText(null);
		courseName.setText(null);
	}

	/**
	 * An override from <code>CallRespondSqlEvent</code>, updates the
	 * course, professor, and university JComboBoxes to ensure that
	 * the latest data is present, and to apply any filters the user
	 * has requested via the parent <code>CourseManager</code> class.
	 */
	protected void updateSelectors() {
		PRSFrame.updateSelector(courseSelector, "Course",
				"CIdentifier", currentFilters);
		PRSFrame.updateSelector(courseSelector2, "Course",
				"CIdentifier", currentFilters);
		PRSFrame.updateSelector(professorSelector, "Professor",
				"PName");
		PRSFrame.updateSelector(universitySelector, "University",
				"UName");
		updateProfIds("Professor", "PID");
		initializeLists();
		attributes.add("CIdentifier");
		attributes.add("CName");
		attributes.add("University");
		attributes.add("UniqueId");
		populateArrayList(SQLDatabaseProxy.select("Course",
				attributes, currentFilters));
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

	/**
	 * Populates the back-end ArrayList of the Course data that is
	 * available to be selected by the JCombBox. This allows for
	 * easier use of update and delete statements.
	 * 
	 * @param select
	 *            The results from the select query on the database
	 */
	private void populateArrayList(ArrayList<String[]> select) {
		availableCourses.clear();
		for (String[] s : select) {
			availableCourses.add(new Course(s));
		}
	}

}
