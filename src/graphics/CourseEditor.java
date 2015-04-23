/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * @author Doug Blase
 *
 */
public class CourseEditor extends JPanel {

	private JTextField courseName, courseIdentifier;
	private JComboBox<String> courseSelector, operationSelector,
			universitySelector;

	private JButton submit;

	private static final int ADD = 0;
	private static final int DELETE = 1;
	private static final int MODIFY = 2;

	ArrayList<Object> values;
	ArrayList<String> attributes;

	private CourseManager parent;

	public CourseEditor(CourseManager cm) {
		parent = cm;
		operationSelector = new JComboBox<String>();
		operationSelector.addItem("Add");

		if (PRSFrame.JDBC) {
			operationSelector.addItem("Delete");
			operationSelector.addItem("Modify");
		}
		operationSelector.addItemListener(new ItemResponder());
		courseName = new JTextField();
		courseIdentifier = new JTextField();
		courseSelector = new JComboBox<String>();

		if (PRSFrame.JDBC) {
			// TODO Populate combo box with names
		}
		else courseSelector.addItem("TEMP");

		universitySelector = new JComboBox<String>();
		if (PRSFrame.JDBC) {
			// TODO Get listing from sql.
		}
		else {
			universitySelector.addItem("TEMP");
		}

		courseSelector.setEditable(false);
		courseSelector.setEnabled(false);

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

		add(submit, gbc);
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
			else {
				if (courseSelector.isEnabled()) {
					// TODO Populate text fields with data
				}
			}
		}
	}

	private class ButtonResponder implements ActionListener {

		public void actionPerformed(ActionEvent e) {
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
	}

	private void initializeLists() {
		values = new ArrayList<Object>();
		attributes = new ArrayList<String>();
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

	}
}
