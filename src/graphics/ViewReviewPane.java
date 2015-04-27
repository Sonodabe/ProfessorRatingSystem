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
public class ViewReviewPane extends JPanel {
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

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;

		add(new JLabel("0 - N/A, 1 - Bad, 5 - Good"), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 1;

		add(new JLabel("Class : "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 1;

		add(className, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 2;

		add(new JLabel("Professor : "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 2;

		add(professorName, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 3;

		add(new JLabel("Year: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 3;

		add(year, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 4;

		add(new JLabel("Semester: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 4;

		add(semester, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 5;

		add(new JLabel("Engagement: "), gbc);

		for (int i = 0; i < engagement.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = 5;
			add(engagement[i], gbc);
		}
		// add(engagement, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 6;

		add(new JLabel("Fairness: "), gbc);

		for (int i = 0; i < fairness.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = 6;

			add(fairness[i], gbc);
		}

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 7;

		add(new JLabel("Difficulty of Work: (1 - Easy, 5 - Hard)"),
				gbc);

		for (int i = 0; i < difficultyWork.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = 7;

			add(difficultyWork[i], gbc);
		}
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 8;

		add(new JLabel("Ease of Learning: "), gbc);

		for (int i = 0; i < easeOfLearning.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = 8;

			add(easeOfLearning[i], gbc);
		}

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 9;

		add(new JLabel(
				"Teaching Style: (1 - Entirely Lab Focused, 5 - Entirely Lecture)"),
				gbc);

		for (int i = 0; i < teachingStyle.length; i++) {
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1 + i;
			gbc.gridy = 9;

			add(teachingStyle[i], gbc);
		}

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 10;

		add(new JLabel("Comments: "), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 10;
		gbc.gridheight = 3;

		add(comments, gbc);

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
}
