/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import database.SQLDatabaseProxy;

/**
 * @author Doug Blase
 *
 */
public class CourseEditor extends JPanel {

	private JTextField courseName, courseIdentifier;
	private JComboBox<String> courseSelector, operationSelector,
			universitySelector, professorSelector, courseSelector2;

	private JButton submitCourse, submitTeacherCourse;

	private static final int ADD = 0;
	private static final int DELETE = 1;
	private static final int MODIFY = 2;

	private ArrayList<Object> values;
	private ArrayList<String> attributes;
	private ArrayList<Integer> profIds;

	private CourseManager parent;

	// TODO ENABLE THE BELOW
	/*
	 * Course course; Professor prof;
	 */

	public CourseEditor(CourseManager cm) {
		parent = cm;
		operationSelector = new JComboBox<String>();
		operationSelector.addItem("Add");
		profIds = new ArrayList<Integer>();
		if (PRSFrame.JDBC) {
			operationSelector.addItem("Delete");
			operationSelector.addItem("Modify");
		}
		operationSelector.addItemListener(new ItemResponder());
		courseName = new JTextField();
		courseIdentifier = new JTextField();
		courseSelector = new JComboBox<String>();
		courseSelector2 = new JComboBox<String>();

		universitySelector = new JComboBox<String>();

		professorSelector = new JComboBox<String>();

		courseSelector.setEditable(false);
		courseSelector.setEnabled(false);

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
		add(new JLabel("Course Identifier: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(courseIdentifier, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Course Name: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(courseName, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("University: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(universitySelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.insets = new Insets(0, 0, 100, 0);

		add(submitCourse, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.gridheight = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(new JLabel("Add an instructor for a course:"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 9;

		add(new JLabel("Course: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 9;

		add(courseSelector2, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 10;

		add(new JLabel("Professor: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 10;

		add(professorSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 11;

		add(submitTeacherCourse, gbc);

		this.updateSelectors();
	}

	private class ItemResponder implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == operationSelector) {
				switch (operationSelector.getSelectedIndex()) {
				case ADD:
					courseSelector.setEnabled(false);
					break;
				default:
					courseSelector.setEnabled(true);
					// TODO populate text fields with data
					break;
				}
			}
			else if (e.getSource() == professorSelector) {
				// TODO get Professor data
			}
			else {
				if (courseSelector.isEnabled()) {
					// TODO Populate text fields with Course data
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
					if (PRSFrame.JDBC) {
						// TODO Call SQL
					}
					break;
				case MODIFY:
					// TODO SQL staements
					break;
				case DELETE:
					// TODO SQL staements
					break;
				}
			}
			else {
				buildAddCourseTeacher();
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
	protected void buildAddCourseTeacher() {
		initializeLists();
		attributes.add("CNumber");
		attributes.add("PID");
		// TODO add values for above and then call the SQL manager.
		values.add(courseSelector2.getSelectedItem());
		values.add(profIds.get(professorSelector.getSelectedIndex()));
		SQLDatabaseProxy.insert("Teaches", attributes, values);
	}

	/**
	 * @return
	 */
	protected void buildAdd() {
		initializeLists();
		if (!courseName.getText().trim().isEmpty()) {
			attributes.add("CName");
			values.add(courseName.getText());
		}
		if (!courseIdentifier.getText().trim().isEmpty()) {
			attributes.add("CIdentifier");
			values.add(courseIdentifier.getText());
		}
		attributes.add("University");
		values.add(universitySelector.getItemAt(universitySelector
				.getSelectedIndex()));

		System.out.println(SQLDatabaseProxy.insert("Course",
				attributes, values));

	}

	public void updateSelectors() {
		updateSelector(courseSelector2, "Course", "CName");
		updateSelector(professorSelector, "Professor", "PName");
		updateSelector(universitySelector, "University", "UName");
		updateProfIds(profIds, "Professor", "PID");
	}

	/**
	 * @param profIds2
	 * @param string
	 * @param string2
	 */
	private void updateProfIds(ArrayList<Integer> idList,
			String tableName, String fieldName) {
		ArrayList<String[]> records;

		// TODO Make static and stick somewhere
		ArrayList<String> atts = new ArrayList<String>();
		atts.add(fieldName);

		records = SQLDatabaseProxy.select(tableName, atts);

		idList.clear();

		for (String[] arr : records) {
			idList.add(Integer.parseInt(arr[0]));
		}
	}

	// TODO allow for a filter of some sort...
	public void updateSelector(JComboBox<String> comboBox,
			String tableName, String fieldName) {
		ArrayList<String[]> records;

		// TODO Make static and stick somewhere
		ArrayList<String> atts = new ArrayList<String>();
		atts.add(fieldName);

		records = SQLDatabaseProxy.select(tableName, atts);

		comboBox.removeAllItems();

		for (String[] arr : records) {
			comboBox.addItem(arr[0]);
		}
	}
}
