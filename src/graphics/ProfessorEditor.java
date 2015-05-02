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

	private ArrayList<Object> values;
	private ArrayList<String> attributes;
	private ArrayList<Professor> availableProfessors;

	// private ArrayList<AttributeValue> currentFilters;

	/**
	 * Instantiates this panel, and defines the layout.
	 * 
	 * @param pm
	 *            The parent panel for this instance.
	 */
	public ProfessorEditor(ProfessorManager pm) {
		parent = pm;
		availableProfessors = new ArrayList<Professor>();
		// currentFilters = new ArrayList<AttributeValue>();
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
					populateFields();
					break;
				}
			}
			else {
				if (professorSelector.isEnabled()) {
					populateFields();
				}
			}
		}
	}

	private class ButtonResponder implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			switch (operationSelector.getSelectedIndex()) {
			case ADD:
				buildAdd();
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

	/**
	 * Initializes/clears the lists of attributes and values that are
	 * to become part of the next query call to the SQLDatabaseProxy
	 * class.
	 */
	private void initializeLists() {
		values = new ArrayList<Object>();
		attributes = new ArrayList<String>();
	}

	/**
	 * Populates the fields of the form with the data for the
	 * currently selected professor.
	 */
	protected void populateFields() {
		currentProf = availableProfessors.get(professorSelector
				.getSelectedIndex());
		professorName.setText(currentProf.getName());
		researchArea.setText(currentProf.getResearchArea());
		bio.setText(currentProf.getBio());
		yearsWorked.setValue(currentProf.getYearsWorked());
	}

	/**
	 * Sets the fields in this form to their default (blank) values.
	 */
	protected void clearFields() {
		professorName.setText(null);
		researchArea.setText(null);
		bio.setText(null);
		yearsWorked.setValue(0);
	}

	/**
	 * Builds an insert request to be passed to the SQLDatabaseProxy
	 * class.
	 */
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
			// Notify all JPanels that the database has changed in
			// some way.
			super.sqlChanged();
			clearFields();
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
				"PName", currentFilters);
		initializeLists();
		attributes.add("PID");
		attributes.add("PName");
		attributes.add("ResearchArea");
		attributes.add("Bio");
		attributes.add("YearsWorked");
		populateArrayList(SQLDatabaseProxy.select("Professor",
				attributes, currentFilters));
	}

	/**
	 * Populates the back-end ArrayList of the Professor data that is
	 * available to be selected by the JCombBox. This allows for
	 * easier use of update and delete statements.
	 * 
	 * @param select
	 *            The results from the select query on the database
	 */
	private void populateArrayList(ArrayList<String[]> select) {
		availableProfessors.clear();
		for (String[] s : select) {
			availableProfessors.add(new Professor(s));
		}
	}

}
