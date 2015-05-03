/**
 * 
 */
package graphics;

//import graphics.ReviewPane.ButtonResponder;

import java.awt.*;
import java.util.Calendar;

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
	private JButton search;
	private JRadioButton[] engagement, fairness, difficultyWork,
			easeOfLearning, teachingStyle;
	private ButtonGroup engagementGroup, fairnessGroup,
			difficultyWorkGroup, easeOfLearningGroup,
			teachingStyleGroup;

	public ViewReviewPane(ViewReviewTab rt) {
		parent = rt;
		search = new JButton("Search");
		//search.addActionListener(new ButtonResponder());

		className = new JTextField();
		className.setEditable(true);

		professorName = new JTextField();
		professorName.setEditable(true);

		semester = new JComboBox<String>();
		semester.addItem("FALL");
		semester.addItem("WINTER");
		semester.addItem("SPRING");
		semester.addItem("SUMMER");
		semester.addItem(" ");
		semester.setSelectedIndex(4);
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		SpinnerModel yearModel = new SpinnerNumberModel(currentYear,
				1970, currentYear, 1);


		year = new JSpinner(yearModel);
		year.setEditor(new JSpinner.NumberEditor(year, "#"));

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
		y += 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = y;
		
		add(search, gbc);
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
