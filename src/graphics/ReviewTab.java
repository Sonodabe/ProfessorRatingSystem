package graphics;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author Doug Blase
 *
 */
public class ReviewTab extends JPanel {

	private ReviewPane rp;

	public ReviewTab() {
		setLayout(new GridLayout(1, 1));

		rp = new ReviewPane(this);
		add(rp);
	}
}
