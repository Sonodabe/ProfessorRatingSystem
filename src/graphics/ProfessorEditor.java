/**
 * 
 */
package graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Doug Blase
 *
 */
public class ProfessorEditor extends JPanel {
	/**
	 * Keeping Eclipse happy
	 */
	private static final long serialVersionUID = 7834761198935229285L;

	private static final String ADD_STATEMENT = "insert into Professor ";
	private static final String DELETE_STATEMENT = "delete from Professor where PID = x";
	private static final String UPDATE_STATEMENT = "update Professor set ";

	private static final int ADD = 0;
	private static final int DELETE = 1;
	private static final int MODIFY = 2;

	private JTextField id, professorName, researchArea;
	private JSpinner yearsWorked;
	private JComboBox<String> professorSelector, operationSelector;
	private JButton submit;

	public ProfessorEditor() {
		operationSelector = new JComboBox<String>();
		operationSelector.addItem("Add");

		if (PRSFrame.JDBC) {
			operationSelector.addItem("Delete");
			operationSelector.addItem("Modify");
		}
		operationSelector.addItemListener(new ItemResponder());
		professorName = new JTextField();
		researchArea = new JTextField();
		id = new JTextField();
		professorSelector = new JComboBox<String>();
		professorSelector.addItemListener(new ItemResponder());
		if (PRSFrame.JDBC) {
			// Populate combo box with names
		}
		else professorSelector.addItem("TEMP");

		professorSelector.setEditable(false);
		professorSelector.setEnabled(false);

		submit = new JButton("Submit");
		submit.addActionListener(new ButtonResponder());

		SpinnerNumberModel snm = new SpinnerNumberModel();
		snm.setMinimum(0);
		snm.setMaximum(100);

		yearsWorked = new JSpinner(snm);

		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;

		add(operationSelector, gbc);
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
				String cmd = ADD_STATEMENT;

				if (PRSFrame.JDBC) {
					// TODO Handle null input
				}
				else System.out.println(cmd + ";");
				break;
			case MODIFY:
				// TODO SQL staements
				break;
			case DELETE:
				// TODO SQL staements
				break;
			}
		}
	}

}
