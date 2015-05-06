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
		PRSFrame.updateSelector(courseSelector, "Course",
				"CIdentifier", currentFilters);
		PRSFrame.updateSelector(professorSelector, "Professor",
				"PName");
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
		values.add(courseSelector.getSelectedItem());
		values.add(profIds.get(professorSelector.getSelectedIndex()));

		if (SQLDatabaseProxy.insert("Teaches", attributes, values)) {
			super.sqlChanged();
		}
	}

	private void initializeLists() {
		values = new ArrayList<Object>();
		attributes = new ArrayList<String>();
	}

}
