package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import database.SQLDatabaseProxy;

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
		classSelector = new JComboBox<String>();
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

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;

		add(new JLabel("0 - N/A, 1 - Bad, 5 - Good"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 1;

		add(new JLabel("Class and Teacher: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 1;

		add(classSelector, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 2;

		add(new JLabel("Year: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 2;

		add(year, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 3;

		add(new JLabel("Semester: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 3;

		add(semester, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 4;

		add(new JLabel("Engagement: "), gbc);

		for (int i = 0; i < engagement.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = 4;
			add(engagement[i], gbc);
		}
		// add(engagement, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 5;

		add(new JLabel("Fairness: "), gbc);

		for (int i = 0; i < fairness.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = 5;

			add(fairness[i], gbc);
		}

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 6;

		add(new JLabel("Difficulty of Work: (1 - Easy, 5 - Hard)"),
				gbc);

		for (int i = 0; i < difficultyWork.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = 6;

			add(difficultyWork[i], gbc);
		}
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 7;

		add(new JLabel("Ease of Learning: "), gbc);

		for (int i = 0; i < easeOfLearning.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = 7;

			add(easeOfLearning[i], gbc);
		}

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 8;

		add(new JLabel(
				"Teaching Style: (1 - Entirely Lab Focused, 5 - Entirely Lecture)"),
				gbc);

		for (int i = 0; i < teachingStyle.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = 8;

			add(teachingStyle[i], gbc);
		}

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 9;

		add(new JLabel("Comments: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.gridheight = 3;

		add(comments, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 12;

		add(submit, gbc);

		populateSelectors();
	}

	/**
	 * 
	 */
	private void populateSelectors() {
		PRSFrame.updateSelector(username, "Student", "Username");
		PRSFrame.updateSelector(classSelector, "Teaches", "CNumber");
		// PRSFrame.updateSelector(professorSelector, "Teaches",
		// fieldName);
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

		attributes.add("Year");
		values.add(year.getValue());

		/*
		 * if (engagement.getValue() != (Integer) 0) {
		 * attributes.add("Engagement");
		 * values.add(engagement.getValue()); }
		 */

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

		if (!comments.getText().trim().isEmpty()) {
			attributes.add("Comments");
			values.add(comments.getText().trim());
		}
		SQLDatabaseProxy.insert("Review", attributes, values);
	}

	/**
	 * @param engagementGroup2
	 * @return
	 */
	private Object getRating(ButtonGroup buttonGroup) {
		String s = null;
		for (Enumeration<AbstractButton> buttons = engagementGroup
				.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				s = button.getText();
				break;
			}
		}
		return s.equals("N/A") ? null : Integer.parseInt(s);
	}
}
