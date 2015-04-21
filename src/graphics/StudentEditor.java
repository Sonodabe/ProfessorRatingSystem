/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Contains the fields required to insert, delete, or modify a student
 * entry.
 * 
 * @author Doug Blase
 *
 */
public class StudentEditor extends JPanel {

	private JTextField major, studentName;
	private JComboBox<String> studentSelector, operationSelector;
	private JButton submit;

	private static final String ADD_STATEMENT = "insert into Student (Sname, Major) values";
	private static final String DELETE_STATEMENT = "delete from Student where SID = x";
	private static final String UPDATE_STATEMENT = "update Student set Sname = X, Major = Y where SID = Z";

	private static final int ADD = 0;
	private static final int DELETE = 1;
	private static final int MODIFY = 2;

	/**
	 * 
	 */
	public StudentEditor() {
		operationSelector = new JComboBox<String>();
		operationSelector.addItem("Add");

		if (PRSFrame.JDBC) {
			operationSelector.addItem("Delete");
			operationSelector.addItem("Modify");
		}
		operationSelector.addItemListener(new ItemResponder());
		studentName = new JTextField();
		major = new JTextField();
		studentSelector = new JComboBox<String>();
		studentSelector.addItemListener(new ItemResponder());
		if (PRSFrame.JDBC) {
			// Populate combo box with names
		}
		else studentSelector.addItem("TEMP");

		studentSelector.setEditable(false);
		studentSelector.setEnabled(false);

		submit = new JButton("Submit");
		submit.addActionListener(new ButtonResponder());
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
		add(new JLabel("Existing Student :"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 1;
		// gbc.gridwidth = 1;
		add(studentSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("Major :"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 3;

		add(major, gbc);

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
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;

		add(submit, gbc);

	}

	private class ItemResponder implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == operationSelector) {
				switch (operationSelector.getSelectedIndex()) {
				case ADD:
					studentSelector.setEnabled(false);
					break;
				default:
					studentSelector.setEnabled(true);
					// TODO populate text fields with data
					break;
				}
			}
			else {
				if (studentSelector.isEnabled()) {
					// TODO Populate text fields with data
				}
			}
		}
	}

	private class ButtonResponder implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			switch (operationSelector.getSelectedIndex()) {
			case ADD:
				String cmd = buildAdd();
				if (cmd.equals("")) {
					break;
				}
				if (PRSFrame.JDBC) {
					// TODO Handle null input
				}
				else System.out.println(cmd + ";");
				break;
			case MODIFY:
				// TODO SQL staements
				break;
			case DELETE:
				// TODO SQL staements
				break;
			}
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
		if (!PRSFrame.JDBC) {
			cmd += " (\"" + studentName.getText() + "\"";
			if (major.getText().trim().isEmpty()) {
				cmd += ", NULL)";
			}
			else cmd += ", \"" + major.getText().trim() + "\")";
		}
		return cmd;
	}

}
