/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import database.*;
import database.AttributeValue;

/**
 * Contains the fields required to indelete, or modify a student
 * entry.
 * 
 * @author Doug Blase
 *
 */
public class StudentEditor extends CallRespondSqlEvent {

	private JComboBox<String> studentSelector;
	private JButton submit;

	private StudentManager parent;

	ArrayList<Object> values;
	ArrayList<String> attributes;

	/**
	 * 
	 */
	public StudentEditor(StudentManager sm) {
		parent = sm;

		studentSelector = new JComboBox<String>();

		studentSelector.setEditable(false);

		submit = new JButton("Delete Student");
		submit.addActionListener(new ButtonResponder());
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

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
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;

		add(submit, gbc);
		addPanel(this);
		updateSelectors();

	}

	private class ButtonResponder implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			ArrayList<AttributeValue> condition = new ArrayList<AttributeValue>();
			condition.add(new AttributeValue("SID", Integer
					.parseInt((String) studentSelector
							.getSelectedItem())));
			SQLDatabaseProxy.delete("Student", condition);
			sqlChanged();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphics.CallRespondSqlEvent#updateSelectors()
	 */
	@Override
	protected void updateSelectors() {
		PRSFrame.updateSelector(studentSelector, "Student", "SID",
				currentFilters);
	}

}
