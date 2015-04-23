package graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * 
 */

/**
 * @author Doug Blase
 *
 */
public class ReviewPane extends JPanel {

	private JSpinner year, engagement, fairness, difficultyWork,
			easeOfLearning, teachingStyle;
	private JComboBox<String> semester, username, classTeacher;
	private JTextArea comments;
	private JButton submit;
	private ReviewTab parent;

	ArrayList<Object> values;
	ArrayList<String> attributes;

	public ReviewPane(ReviewTab rt) {
		parent = rt;
		submit = new JButton("Submit");
		submit.addActionListener(new ButtonResponder());
		// TODO probably get rid of the username menu concept.
		username = new JComboBox<String>();
		classTeacher = new JComboBox<String>();
		semester = new JComboBox<String>();
		semester.addItem("FALL");
		semester.addItem("WINTER");
		semester.addItem("SPRING");
		semester.addItem("SUMMER");

		SpinnerNumberModel snm = new SpinnerNumberModel();
		snm.setMinimum(0); // 0 = N/A
		snm.setMaximum(5);

		engagement = new JSpinner(snm);
		fairness = new JSpinner(snm);
		difficultyWork = new JSpinner(snm);
		easeOfLearning = new JSpinner(snm);
		teachingStyle = new JSpinner(snm);

		SpinnerNumberModel snm2 = new SpinnerNumberModel();
		snm2.setMinimum(1970); // 0 = N/A
		snm2.setMaximum(Calendar.getInstance().get(Calendar.YEAR));
		year = new JSpinner(snm2);
		year.setValue(Calendar.getInstance().get(Calendar.YEAR));

		comments = new JTextArea();

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

		add(classTeacher, gbc);

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

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 4;

		add(engagement, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 5;

		add(new JLabel("Fairness: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 5;

		add(fairness, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 6;

		add(new JLabel("Difficulty of Work: (1 - Easy, 5 - Hard)"),
				gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 6;

		add(difficultyWork, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 7;

		add(new JLabel("Ease of Learning: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 7;

		add(easeOfLearning, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 8;

		add(new JLabel(
				"Teaching Style: (1 - Entirely Lab Focused, 5 - Entirely Lecture)"),
				gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 8;

		add(teachingStyle, gbc);

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

		if (engagement.getValue() != (Integer) 0) {
			attributes.add("Engagement");
			values.add(engagement.getValue());
		}

		if (fairness.getValue() != (Integer) 0) {
			attributes.add("Fairness");
			values.add(fairness.getValue());
		}

		if (difficultyWork.getValue() != (Integer) 0) {
			attributes.add("DifficultyWork");
			values.add(difficultyWork.getValue());
		}

		if (easeOfLearning.getValue() != (Integer) 0) {
			attributes.add("EaseLearning");
			values.add(easeOfLearning.getValue());
		}
		if (teachingStyle.getValue() != (Integer) 0) {
			attributes.add("TeachingStyle");
			values.add(teachingStyle.getValue());
		}
		if (!comments.getText().trim().isEmpty()) {
			attributes.add("Comments");
			values.add(comments.getText().trim());
		}
	}
}
