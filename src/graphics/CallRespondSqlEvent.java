/**
 * 
 */
package graphics;

import java.util.ArrayList;
import javax.swing.JPanel;
import database.AttributeValue;

/**
 * @author Doug Blase
 *
 */
public abstract class CallRespondSqlEvent extends JPanel {
	private static ArrayList<CallRespondSqlEvent> guiComponents = new ArrayList<CallRespondSqlEvent>();
	protected ArrayList<AttributeValue> currentFilters;

	public CallRespondSqlEvent() {
		currentFilters = new ArrayList<AttributeValue>();
	}

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

	/**
	 * A method that calls for a refresh of Data Element JComboBoxes
	 * to reflect any updates to the database that may have occurred
	 * during the execution of this program.
	 */
	protected abstract void updateSelectors();

	/**
	 * Updates the current filters that are used to limit which data
	 * elements are accessible by the child's ComboBox. This method
	 * should generally be called from the parent
	 * <code> Manager </code> classes when a new filter is selected in
	 * the JTable.
	 * 
	 * @param filts
	 *            The filters being used.
	 */
	public void updateFilters(ArrayList<AttributeValue> filts) {
		currentFilters = filts;
		updateSelectors();
	}

}
