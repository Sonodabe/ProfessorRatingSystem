/**
 * 
 */
package graphics;

import java.awt.*;
import javax.swing.*;

/**
 * @author Doug Blase
 *
 */
public class ViewReviewPane extends CallRespondSqlEvent {
	private JSpinner year;
	private JComboBox<String> semester;
	private JTextArea comments;
	private ViewReviewTab parent;
	private JTextField className, professorName;

	private JRadioButton[] engagement, fairness, difficultyWork,
			easeOfLearning, teachingStyle;
	private ButtonGroup engagementGroup, fairnessGroup,
			difficultyWorkGroup, easeOfLearningGroup,
			teachingStyleGroup;

	public ViewReviewPane(ViewReviewTab rt) {
		parent = rt;

		className = new JTextField();
		className.setEditable(false);

		professorName = new JTextField();
		professorName.setEditable(false);

		semester = new JComboBox<String>();
		semester.addItem("FALL");
		semester.addItem("WINTER");
		semester.addItem("SPRING");
		semester.addItem("SUMMER");
		semester.addItem(" ");
		semester.setSelectedIndex(4);

		year = new JSpinner();
		year.setEnabled(false);

		comments = new JTextArea();
		comments.setEditable(false);

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
		add(new JLabel("Engagement: "), gbc);

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

		add(new JLabel("Fairness: "), gbc);

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

		add(new JLabel("Ease of Learning: "), gbc);

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
			buttons[i].setEnabled(false);
			group.add(buttons[i]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphics.CallRespondSqlEvent#updateSelectors()
	 */
	@Override
	protected void updateSelectors() {
		// No combo boxes dependent on other database updates,
		// therefore nothing to do here.
	}
}
