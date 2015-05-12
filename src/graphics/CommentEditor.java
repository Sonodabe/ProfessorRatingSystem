/**
 * 
 */
package graphics;

import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Doug Blase
 *
 */
public class CommentEditor extends JPanel {

	private JTextArea commentsEditor;
	private JButton submit;
	private ViewReviewTab parent;

	public CommentEditor(boolean admin, ViewReviewTab vrt) {
		parent = vrt;
		setLayout(new GridLayout(3, 0));
		commentsEditor = new JTextArea();
		if (admin) {
			add(new JLabel("Edit Comment Below:"));
		}
		else {
			add(new JLabel("Comment for selected review:"));
		}
		add(commentsEditor);
		submit = new JButton("Submit edited comment");
		submit.addActionListener(new ButtonResponder());
		if (admin) {
			add(submit);
		}
	}

	public void populateComments(String text) {
		commentsEditor.setText(text);
	}

	private class ButtonResponder implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			parent.updateComment(commentsEditor.getText().trim());
		}
	}
}
