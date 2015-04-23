/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Contains the fields required to indelete, or modify a student
 * entry.
 * 
 * @author Doug Blase
 *
 */
public class StudentEditor extends JPanel {

	private JTextField major, studentName;
	private JComboBox<String> studentSelector, operationSelector;
	private JButton submit;

	private static final int DELETE = 0;
	private static final int MODIFY = 1;

	private StudentManager parent;

	ArrayList<Object> values;
	ArrayList<String> attributes;

	/**
	 * 
	 */
	public StudentEditor(StudentManager sm) {
		parent = sm;
		operationSelector = new JComboBox<String>();
		operationSelector.addItem("Delete");
		operationSelector.addItem("Modify");
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
		add(new JLabel("Existing Student: "), gbc);

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
		add(new JLabel("Major: "), gbc);

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
			if (e.getSource() == studentSelector) {
				// TODO Create a student variable and populate fields.
			}
		}
	}

	private class ButtonResponder implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			switch (operationSelector.getSelectedIndex()) {
			case MODIFY:
				// TODO SQL staements
				break;
			case DELETE:
				// TODO SQL staements
				break;
			}
		}
	}

}
