/**
 * 
 */
package graphics;

//import graphics.ReviewPane.ButtonResponder;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import database.AttributeValue;

/**
 * @author Doug Blase
 *
 */
public class ViewReviewPane extends CallRespondSqlEvent {
	private JSpinner year;
	private JComboBox<String> semester, studentSelector;
	private JTextArea comments;
	private ViewReviewTab parent;
	private JTextField className, professorName;
	// private JButton search;
	private JCheckBox yearFilterEnabled;
	private boolean isAdmin;

	public ViewReviewPane(ViewReviewTab rt, boolean isAdmin) {
		this.isAdmin = isAdmin;
		parent = rt;
		// search = new JButton("Search");
		// search.addActionListener(new ButtonResponder());

		studentSelector = new JComboBox<String>();
		studentSelector.addItemListener(new ItemResponder());

		className = new JTextField();
		className.setEditable(true);
		className.getDocument().addDocumentListener(
				new SearchResponder());
		professorName = new JTextField();
		professorName.setEditable(true);
		professorName.getDocument().addDocumentListener(
				new SearchResponder());

		semester = new JComboBox<String>();
		semester.addItem("FALL");
		semester.addItem("WINTER");
		semester.addItem("SPRING");
		semester.addItem("SUMMER");
		semester.addItem("ALL");
		semester.setSelectedItem("ALL");
		semester.addItemListener(new ItemResponder());

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		SpinnerModel yearModel = new SpinnerNumberModel(currentYear,
				1970, currentYear, 1);

		year = new JSpinner(yearModel);
		year.setEditor(new JSpinner.NumberEditor(year, "#"));
		year.getModel().addChangeListener(new ChangeResponder());
		year.setEnabled(false);
		yearFilterEnabled = new JCheckBox("Filter by Year");
		yearFilterEnabled.setSelected(false);
		yearFilterEnabled.addChangeListener(new ChangeResponder());

		comments = new JTextArea();
		comments.setEditable(true);

		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		int y = 0;

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y++;

		add(new JLabel("Search by any of the below criteria"), gbc);

		if (isAdmin) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 0;
			gbc.gridy = y;
			gbc.gridwidth = 1;

			add(new JLabel("Student: "), gbc);

			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1;
			gbc.gridy = y++;
			gbc.gridwidth = 6;

			add(studentSelector, gbc);
		}

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 1;

		add(new JLabel("Class: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;
		gbc.gridwidth = 6;

		add(className, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 1;

		add(new JLabel("Professor: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;
		gbc.gridwidth = 6;

		add(professorName, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 1;

		add(new JLabel("Year: "), gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 7;
		gbc.gridy = y;
		gbc.gridwidth = 1;

		add(yearFilterEnabled, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;
		gbc.gridwidth = 6;
		add(year, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		add(new JLabel("Semester: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;
		gbc.gridwidth = 6;

		add(semester, gbc);
		y += 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y;

		// add(search, gbc);
		super.addPanel(this);
		updateSelectors();
		studentSelector.setSelectedItem("ALL");
	}

	private class ChangeResponder implements ChangeListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.swing.event.ChangeListener#stateChanged(javax.swing
		 * .event.ChangeEvent)
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == yearFilterEnabled) {
				year.setEnabled(yearFilterEnabled.isSelected());
			}
			applyFilters();
		}

	}

	private class SearchResponder implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			applyFilters();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			applyFilters();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			applyFilters();
		}
	}

	private class ItemResponder implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			applyFilters();
		}
	}

	/**
	 * 
	 */
	protected void applyFilters() {
		ArrayList<AttributeValue> filter = new ArrayList<AttributeValue>();
		if (!className.getText().trim().isEmpty()) {
			filter.add(new AttributeValue("CID", "UniqueId",
					AttributeValue.JOIN));
			filter.add(new AttributeValue("CIdentifier", className
					.getText().trim(), AttributeValue.LIKE));
		}
		if (!professorName.getText().trim().isEmpty()) {
			filter.add(new AttributeValue("Review.PID",
					"Professor.PID", AttributeValue.JOIN));
			filter.add(new AttributeValue("PName", professorName
					.getText().trim(), AttributeValue.LIKE));
		}

		if (yearFilterEnabled.isSelected()) {
			filter.add(new AttributeValue("Year", year.getValue()));
		}

		if (!semester.getSelectedItem().equals("ALL")) {
			filter.add(new AttributeValue("Semester", semester
					.getSelectedItem()));
		}

		if (!studentSelector.getSelectedItem().equals("ALL")) {
			filter.add(new AttributeValue("SID", studentSelector
					.getSelectedItem()));
		}

		parent.updateFilters(filter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphics.CallRespondSqlEvent#updateSelectors()
	 */
	@Override
	protected void updateSelectors() {
		PRSFrame.updateSelector(studentSelector, "Student", "SID");
		studentSelector.addItem("ALL");
	}
}
