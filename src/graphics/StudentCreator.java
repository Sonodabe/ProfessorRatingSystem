/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import data.Student;

/**
 * Contains the fields required to insert, delete, or modify a student
 * entry.
 * 
 * @author Doug Blase
 *
 */
public class StudentCreator extends JPanel {

	private JTextField major, studentName, userName;
	private JComboBox<String> universitySelector;
	private JButton submit;

	private static final String ADD_STATEMENT = "insert into Student values";

	private static final int ADD = 0;
	private Student student;

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
		if (PRSFrame.JDBC) {
			// TODO Get listing from sql.
		}
		else {
			universitySelector.addItem("TEMP");
		}
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

	}

	private class ButtonResponder implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String cmd = buildAdd();
			if (cmd.equals("")) {
				return;
			}
			if (PRSFrame.JDBC) {
				// TODO Handle null input
			}
			else System.out.println(cmd + ";");
		}

	}

	/**
	 * @return
	 */
	public String buildAdd() {
		String cmd = ADD_STATEMENT;
		if (studentName.getText().trim().isEmpty()) {
			return "";
		}

		return cmd;
	}

}
