/**
 * 
 */
package graphics;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * @author Doug Blase
 *
 */
public abstract class CallRespondSqlEvent extends JPanel {
	private static ArrayList<CallRespondSqlEvent> guiComponents = new ArrayList<CallRespondSqlEvent>();

	// public CallRespondSqlEvent() {
	// guiComponents.add(child);
	// }
	protected void addPanel(CallRespondSqlEvent child) {
		guiComponents.add(child);
	}

	protected void sqlChanged() {
		for (CallRespondSqlEvent tab : guiComponents) {
			tab.updateSelectors();
		}
	}

	protected abstract void updateSelectors();

}
