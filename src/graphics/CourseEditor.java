/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Doug Blase
 *
 */
public class CourseEditor extends JPanel {

	private JTextField courseName, courseNumber;
	private JComboBox<String> courseSelector, operationSelector;

	private JButton submit;

	private static final String ADD_STATEMENT = "insert into Course ";
	private static final String DELETE_STATEMENT = "delete from Course ";
	private static final String UPDATE_STATEMENT = "update Course set ";

	private static final int ADD = 0;
	private static final int DELETE = 1;
	private static final int MODIFY = 2;

	public CourseEditor() {
		operationSelector = new JComboBox<String>();
		operationSelector.addItem("Add");

		if (PRSFrame.JDBC) {
			operationSelector.addItem("Delete");
			operationSelector.addItem("Modify");
		}
		operationSelector.addItemListener(new ItemResponder());
		courseName = new JTextField();
		courseNumber = new JTextField();
		courseSelector = new JComboBox<String>();

		if (PRSFrame.JDBC) {
			// Populate combo box with names
		}
		else courseSelector.addItem("TEMP");

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
		add(new JLabel("Course Number: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(courseNumber, gbc);

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
		gbc.gridx = 1;
		gbc.gridy = 3;
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
		return null;
	}
}
