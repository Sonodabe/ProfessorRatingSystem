package graphics;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import database.*;
import database.AttributeValue;

/**
 * 
 */

/**
 * @author Doug Blase
 *
 */
public class ReviewPane extends CallRespondSqlEvent {

	private JSpinner year;
	private JComboBox<String> semester, username, courseSelector,
			professorSelector, universitySelector;
	private JTextArea comments;
	private JButton submit;
	private ReviewTab parent;

	private JRadioButton[] engagement, fairness, difficultyWork,
			easeOfLearning, teachingStyle;
	private ButtonGroup engagementGroup, fairnessGroup,
			difficultyWorkGroup, easeOfLearningGroup,
			teachingStyleGroup;

	ArrayList<Object> values;
	ArrayList<String> attributes;

	// ArrayList<Student> students;

	public ReviewPane(ReviewTab rt) {
		parent = rt;
		submit = new JButton("Submit");
		submit.addActionListener(new ButtonResponder());
		username = new JComboBox<String>();
		courseSelector = new JComboBox<String>();
		courseSelector.addItemListener(new ItemResponder());
		professorSelector = new JComboBox<String>();
		universitySelector = new JComboBox<String>();
		universitySelector.addItemListener(new ItemResponder());
		semester = new JComboBox<String>();
		semester.addItem("FALL");
		semester.addItem("WINTER");
		semester.addItem("SPRING");
		semester.addItem("SUMMER");

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		SpinnerModel yearModel = new SpinnerNumberModel(currentYear,
				1970, currentYear, 1);

		year = new JSpinner(yearModel);
		year.setEditor(new JSpinner.NumberEditor(year, "#"));

		comments = new JTextArea();

		engagement = new JRadioButton[6];
		engagementGroup = new ButtonGroup();
		initializeButtonGroup(engagement, engagementGroup);

		fairness = new JRadioButton[6];
		fairnessGroup = new ButtonGroup();
		initializeButtonGroup(fairness, fairnessGroup);

		difficultyWork = new JRadioButton[6];
		difficultyWorkGroup = new ButtonGroup();
		initializeButtonGroup(difficultyWork, difficultyWorkGroup);

		easeOfLearning = new JRadioButton[6];
		easeOfLearningGroup = new ButtonGroup();
		initializeButtonGroup(easeOfLearning, easeOfLearningGroup);

		teachingStyle = new JRadioButton[6];
		teachingStyleGroup = new ButtonGroup();
		initializeButtonGroup(teachingStyle, teachingStyleGroup);

		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		int y = 0;

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y++;

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("User: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;
		gbc.gridwidth = 6;

		add(username, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 1;

		add(new JLabel("Course: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;
		gbc.gridwidth = 6;

		add(courseSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 1;

		add(new JLabel("University: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;
		gbc.gridwidth = 6;

		add(universitySelector, gbc);

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

		add(professorSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 1;

		add(new JLabel("Year: "), gbc);

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

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		add(new JLabel("Engagement (1 - Poor Engagement, 5 - Full Engagement): "), gbc);

		for (int i = 0; i < engagement.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = y;
			add(engagement[i], gbc);
		}
		y++;
		// add(engagement, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Fairness (1 - Unfair Teacher, 5 - Fair Teacher): "), gbc);

		for (int i = 0; i < fairness.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = y;

			add(fairness[i], gbc);
		}
		y++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Difficulty of Work: (1 - Easy, 5 - Hard)"),
				gbc);

		for (int i = 0; i < difficultyWork.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = y;

			add(difficultyWork[i], gbc);
		}
		y++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Ease of Learning (1 - Hard to learn, 5 - Easy to learn): "), gbc);

		for (int i = 0; i < easeOfLearning.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = y;

			add(easeOfLearning[i], gbc);
		}
		y++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel(
				"Teaching Style: (1 - Entirely Lab Focused, 5 - Entirely Lecture)"),
				gbc);

		for (int i = 0; i < teachingStyle.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = y;

			add(teachingStyle[i], gbc);
		}
		y++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Comments: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y;
		gbc.gridheight = 3;
		gbc.gridwidth = 6;

		add(comments, gbc);
		y += 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y;

		add(submit, gbc);
		super.addPanel(this);
		updateSelectors();
	}

	private void initializeButtonGroup(JRadioButton buttons[],
			ButtonGroup group) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JRadioButton(i == 0 ? "N/A" : "" + i);
			if (i == 0) {
				buttons[i].setSelected(true);
			}
			group.add(buttons[i]);
		}
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
	 * 
	 */
	protected void buildAdd() {
		initializeLists();

		attributes.add("SID");

		values.add(getStudentId());

		attributes.add("PID");
		values.add(getProfessorId(professorSelector
				.getItemAt(professorSelector.getSelectedIndex())));

		attributes.add("CID");
		values.add(getCourse());

		attributes.add("Semester");
		values.add(semester.getSelectedItem());

		attributes.add("Year");
		values.add(year.getValue());

		attributes.add("Engagement");
		values.add(getRating(engagementGroup));

		attributes.add("Fairness");
		values.add(getRating(fairnessGroup));

		attributes.add("DifficultyWork");
		values.add(getRating(difficultyWorkGroup));

		attributes.add("EaseLearning");
		values.add(getRating(easeOfLearningGroup));

		attributes.add("TeachingStyle");
		values.add(getRating(teachingStyleGroup));
		attributes.add("Comments");
		if (!comments.getText().trim().isEmpty()) {
			values.add(comments.getText().trim());
		}
		else {
			values.add(null);
		}
		try {
			if (SQLDatabaseProxy.insert("Review", attributes, values)) {
				sqlChanged();
				comments.setText("");
			}
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	/**
	 * @return
	 */
	private Integer getStudentId() {
		ArrayList<String> atts = new ArrayList<String>();
		atts.add("SID");
		ArrayList<AttributeValue> filter = new ArrayList<AttributeValue>();
		filter.add(new AttributeValue("Username", username
				.getSelectedItem()));
		ArrayList<String[]> results = SQLDatabaseProxy.select(
				"Student", attributes, filter);
		return Integer.parseInt((results.get(0))[0]);
	}

	/**
	 * @return
	 */
	private Integer getCourse() {
		ArrayList<String> atts = new ArrayList<String>();
		atts.add("UniqueId");
		ArrayList<AttributeValue> filter = new ArrayList<AttributeValue>();
		filter.add(new AttributeValue("CIdentifier", courseSelector
				.getSelectedItem()));
		filter.add(new AttributeValue("University",
				universitySelector.getSelectedItem()));
		ArrayList<String[]> results = SQLDatabaseProxy.select(
				"Course", atts, filter);
		return Integer.parseInt((results.get(0))[0]);
	}

	/**
	 * @param itemAt
	 * @return
	 */
	private Integer getProfessorId(String itemAt) {
		ArrayList<AttributeValue> filters = new ArrayList<AttributeValue>();
		filters.add(new AttributeValue("PName", itemAt));
		filters.add(new AttributeValue("Teaches.PID",
				"Professor.PID", AttributeValue.JOIN));
		filters.add(new AttributeValue("CNumber", getCourse()));

		ArrayList<String> atts = new ArrayList<String>();
		atts.add("Professor.PID");
		ArrayList<String[]> results = SQLDatabaseProxy.select(
				"Professor,Teaches", atts, filters);
		return Integer.parseInt((results.get(0))[0]);
	}

	/**
	 * @param engagementGroup2
	 * @return
	 */
	private Object getRating(ButtonGroup buttonGroup) {
		String s = null;
		for (Enumeration<AbstractButton> buttons = buttonGroup
				.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				s = button.getText();
				break;
			}
		}
		return s.equals("N/A") ? null : Integer.parseInt(s);
	}

	private class ItemResponder implements ItemListener {
		public void itemStateChanged(ItemEvent e) {

			if (e.getSource() == courseSelector) {
				if (courseSelector.getItemCount() > 0) {
					refreshUniversityList();
				}
			}
			else if (e.getSource() == universitySelector) {
				if (universitySelector.getItemCount() > 0) {
					refreshProfList();
				}
			}

		}
	}

	protected void refreshProfList() {
		ArrayList<AttributeValue> filters = new ArrayList<AttributeValue>();

		filters.add(new AttributeValue("CNumber", getCourse()));

		filters.add(new AttributeValue("Teaches.PID",
				"Professor.PID", AttributeValue.JOIN));

		PRSFrame.updateSelector(professorSelector,
				"Professor, Teaches", "PName", filters);
	}

	/**
	 * 
	 */
	protected void refreshUniversityList() {
		ArrayList<AttributeValue> courseFilt = new ArrayList<AttributeValue>();
		courseFilt.add(new AttributeValue("CIdentifier",
				courseSelector.getSelectedItem()));
		PRSFrame.updateSelector(universitySelector, "Course",
				"University", courseFilt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphics.CallRespondSqlEvent#updateSelectors()
	 */
	@Override
	protected void updateSelectors() {
		PRSFrame.updateSelector(username, "Student", "Username");
		ArrayList<AttributeValue> filters = new ArrayList<AttributeValue>();
		filters.add(new AttributeValue("Teaches.CNumber",
				"Course.UniqueId", AttributeValue.JOIN));
		PRSFrame.updateSelector(courseSelector, "Course,Teaches",
				"Distinct CIdentifier");
	}
}
