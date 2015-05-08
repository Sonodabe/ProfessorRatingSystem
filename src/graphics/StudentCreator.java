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
		userName = new JTextField();

		GridBagConstraints gbc = new GridBagConstraints();
		int y = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Student Name: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;

		add(studentName, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		add(new JLabel("User Name: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;
		add(userName, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		add(new JLabel("Major :"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;

		add(major, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y;
		gbc.gridwidth = 2;

		add(submit, gbc);
		super.addPanel(this);
	}

	private class ButtonResponder implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			buildAdd();
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
