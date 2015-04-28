/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import data.Student;
import database.SQLDatabaseProxy;

/**
 * Contains the fields required to insert, delete, or modify a student
 * entry.
 * 
 * @author Doug Blase
 *
 */
public class StudentCreator extends CallRespondSqlEvent {

	private JTextField major, studentName, userName;
	private JComboBox<String> universitySelector;
	private JButton submit;

	private static final int ADD = 0;
	private Student student;

	ArrayList<Object> values;
	ArrayList<String> attributes;

	/**
	 * 
	 */
	public StudentCreator() {
		studentName = new JTextField();
		major = new JTextField();

		submit = new JButton("Submit");
		submit.addActionListener(new ButtonResponder());
		setLayout(new GridBagLayout());
		universitySelector = new JComboBox<String>();
		PRSFrame.updateSelector(universitySelector, "University",
				"UName");
		userName = new JTextField();

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 2;

		add(new JLabel("Student Name: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 2;

		add(studentName, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("User Name: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(userName, gbc);

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
		gbc.gridx = 0;
		gbc.gridy = 5;
		add(new JLabel("Major :"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 5;

		add(major, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 2;

		add(submit, gbc);
		super.addPanel(this);
	}

	private class ButtonResponder implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			buildAdd();
			if (PRSFrame.JDBC) {
				// TODO Handle null input
			}
		}

	}

	private void initializeLists() {
		values = new ArrayList<Object>();
		attributes = new ArrayList<String>();
	}

	/**
	 * @return
	 */
	public void buildAdd() {
		initializeLists();
		if (!studentName.getText().trim().isEmpty()) {
			attributes.add("SName");
			values.add(studentName.getText());
		}
		if (!userName.getText().trim().isEmpty()) {
			attributes.add("Username");
			values.add(userName.getText());
		}
		if (!major.getText().trim().isEmpty()) {
			attributes.add("Major");
			values.add(major.getText());
		}
		attributes.add("University");
		values.add(universitySelector.getItemAt(universitySelector
				.getSelectedIndex()));

		if (SQLDatabaseProxy.insert("Student", attributes, values)) {
			super.sqlChanged();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphics.CallRespondSqlEvent#updateSelectors()
	 */
	@Override
	protected void updateSelectors() {
		// No selectors to be had, therefore no usefullness
	}

}
