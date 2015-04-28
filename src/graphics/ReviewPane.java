package graphics;

import java.awt.*;
import java.awt.event.*;
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
public class ReviewPane extends JPanel {

	private JSpinner year;
	private JComboBox<String> semester, username, classSelector,
			professorSelector;
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

	public ReviewPane(ReviewTab rt) {
		parent = rt;
		submit = new JButton("Submit");
		submit.addActionListener(new ButtonResponder());
		// TODO probably get rid of the username menu concept.
		username = new JComboBox<String>();
		username.addItemListener(new ItemResponder());
		classSelector = new JComboBox<String>();
		classSelector.addItemListener(new ItemResponder());
		professorSelector = new JComboBox<String>();
		semester = new JComboBox<String>();
		semester.addItem("FALL");
		semester.addItem("WINTER");
		semester.addItem("SPRING");
		semester.addItem("SUMMER");

		SpinnerNumberModel snm2 = new SpinnerNumberModel();
		snm2.setMinimum(1970); // 0 = N/A
		snm2.setMaximum(Calendar.getInstance().get(Calendar.YEAR));
		year = new JSpinner(snm2);
		year.setValue(Calendar.getInstance().get(Calendar.YEAR));

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

		add(new JLabel("0 - N/A, 1 - Bad, 5 - Good"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("User: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;

		add(username, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Class: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;

		add(classSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Professor: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;

		add(professorSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Year: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;

		add(year, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Semester: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y++;

		add(semester, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Engagement: "), gbc);

		for (int i = 0; i < engagement.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 2 + i;
			gbc.gridy = y;
			add(engagement[i], gbc);
		}
		y++;
		// add(engagement, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Fairness: "), gbc);

		for (int i = 0; i < fairness.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 2 + i;
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
			gbc.gridx = 2 + i;
			gbc.gridy = y;

			add(difficultyWork[i], gbc);
		}
		y++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = y;

		add(new JLabel("Ease of Learning: "), gbc);

		for (int i = 0; i < easeOfLearning.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 2 + i;
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
			gbc.gridx = 2 + i;
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

		add(comments, gbc);
		y += 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y;

		add(submit, gbc);

		populateSelectors();
	}

	/**
	 * 
	 */
	private void populateSelectors() {
		PRSFrame.updateSelector(username, "Student", "Username");
		PRSFrame.updateSelector(classSelector, "Teaches", "CNumber");
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
		// since DB is 1 based indexing
		values.add(username.getSelectedIndex() + 1);

		attributes.add("PID");
		values.add(getProfessorId(professorSelector
				.getItemAt(professorSelector.getSelectedIndex())));

		attributes.add("CID");
		values.add(classSelector.getSelectedItem());

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
		SQLDatabaseProxy.insert("Review", attributes, values);
	}

	/**
	 * @param itemAt
	 * @return
	 */
	private Integer getProfessorId(String itemAt) {
		ArrayList<AttributeValue> filters = new ArrayList<AttributeValue>();
		filters.add(new AttributeValue("PName", itemAt));
		filters.add(new AttributeValue("Course.CIdentifier",
				classSelector.getItemAt(classSelector
						.getSelectedIndex())));
		filters.add(new AttributeValue("Teaches.PID",
				"Professor.PID", AttributeValue.JOIN));
		ArrayList<String> atts = new ArrayList<String>();
		atts.add("Professor.PID");
		ArrayList<String[]> results = SQLDatabaseProxy.select(
				"Professor,Course,Teaches", atts, filters);
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

			if (e.getSource() == classSelector) {
				if (classSelector.getItemCount() > 0) {
					refreshProfList();
				}
			}
			else if (e.getSource() == username) {
				if (username.getItemCount() > 0) {
					refreshCourseList();
					// refreshProfList();
				}
			}

		}
	}

	protected void refreshProfList() {
		ArrayList<AttributeValue> filters = new ArrayList<AttributeValue>();

		filters.add(new AttributeValue("Teaches.CNumber",
				classSelector.getItemAt(classSelector
						.getSelectedIndex()), AttributeValue.EQUAL));

		filters.add(new AttributeValue("Teaches.PID",
				"Professor.PID", AttributeValue.JOIN));

		PRSFrame.updateSelector(professorSelector,
				"Professor, Teaches", "PName", filters);
	}

	/**
	 * 
	 */
	protected void refreshCourseList() {
		ArrayList<AttributeValue> filters = new ArrayList<AttributeValue>();
		filters.add(new AttributeValue("SID", username
				.getSelectedIndex() + 1));
		filters.add(new AttributeValue("Student.University",
				"Course.University", AttributeValue.JOIN));
		PRSFrame.updateSelector(classSelector, "Student,Course",
				"CIdentifier", filters);
	}
}
