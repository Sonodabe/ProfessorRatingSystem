/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import data.Professor;
import database.SQLDatabaseProxy;

/**
 * @author Doug Blase
 *
 */
public class ProfessorEditor extends CallRespondSqlEvent {
	/**
	 * Keeping Eclipse happy
	 */
	private static final long serialVersionUID = 7834761198935229285L;

	private static final int ADD = 0;
	private static final int DELETE = 1;
	private static final int MODIFY = 2;

	private JTextField professorName, researchArea;
	private JTextArea bio;
	private JSpinner yearsWorked;
	private JComboBox<String> professorSelector, operationSelector;
	private JButton submit;

	private ProfessorManager parent;
	private Professor currentProf;

	ArrayList<Object> values;
	ArrayList<String> attributes;

	public ProfessorEditor(ProfessorManager pm) {
		parent = pm;
		operationSelector = new JComboBox<String>();
		operationSelector.addItem("Add");
		operationSelector.addItem("Delete");
		operationSelector.addItem("Modify");
		operationSelector.addItemListener(new ItemResponder());
		professorName = new JTextField();
		researchArea = new JTextField();
		professorSelector = new JComboBox<String>();
		professorSelector.addItemListener(new ItemResponder());

		professorSelector.setEditable(false);
		professorSelector.setEnabled(false);

		submit = new JButton("Submit");
		submit.addActionListener(new ButtonResponder());

		SpinnerNumberModel snm = new SpinnerNumberModel();
		snm.setMinimum(0);
		snm.setMaximum(100);

		yearsWorked = new JSpinner(snm);
		bio = new JTextArea();
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;

		add(operationSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;

		add(new JLabel("Existing Prof: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 1;

		add(professorSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 2;

		add(new JLabel("Prof Name: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 2;

		add(professorName, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 4;

		add(new JLabel("Research Area: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 4;

		add(researchArea, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 5;

		add(new JLabel("Years Employed"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 5;

		add(yearsWorked, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 6;

		add(new JLabel("BIO: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridheight = 3;

		add(bio, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 10;
		gbc.gridheight = 1;

		add(submit, gbc);

		super.addPanel(this);
		this.updateSelectors();

	}

	private class ItemResponder implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == operationSelector) {
				switch (operationSelector.getSelectedIndex()) {
				case ADD:
					professorSelector.setEnabled(false);
					break;
				default:
					professorSelector.setEnabled(true);
					// TODO populate text fields with data
					break;
				}
			}
			else {
				if (professorSelector.isEnabled()) {
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
					// TODO Handle null input
				}
			case MODIFY:
				buildModify();
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

	protected void buildAdd() {
		initializeLists();
		attributes.add("PName");
		if (!professorName.getText().trim().isEmpty()) {
			values.add(professorName.getText());
		}
		else {
			values.add(null);
		}
		attributes.add("ResearchArea");
		if (!researchArea.getText().trim().isEmpty()) {

			values.add(researchArea.getText());
		}
		else {
			values.add(null);
		}
		attributes.add("Bio");
		if (!bio.getText().trim().isEmpty()) {

			values.add(bio.getText());
		}
		else {
			values.add(null);
		}
		attributes.add("YearsWorked");
		values.add(yearsWorked.getValue());
		if (SQLDatabaseProxy.insert("Professor", attributes, values)) {
			super.sqlChanged();
		}
	}

	/**
	 * 
	 */
	protected void buildModify() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphics.CallRespondSqlEvent#updateSelectors()
	 */
	@Override
	protected void updateSelectors() {
		PRSFrame.updateSelector(professorSelector, "Professor",
				"PName");
	}

}
