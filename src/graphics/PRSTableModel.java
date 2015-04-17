/**
 * 
 */
package graphics;

import javax.swing.table.AbstractTableModel;

/**
 * @author Doug Blase
 *
 */
public class PRSTableModel extends AbstractTableModel {

	private String columnNames[];
	private Object rowData[][];

	/**
	 * @param columnNames
	 * @param rowData
	 */
	public PRSTableModel(Object[][] rowData, String[] columnNames) {
		this.columnNames = columnNames;
		this.rowData = rowData;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col].toString();
	}

	public int getRowCount() {
		return rowData.length;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public Object getValueAt(int row, int col) {
		return rowData[row][col];
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void setValueAt(Object value, int row, int col) {
		rowData[row][col] = value;
		fireTableCellUpdated(row, col);
	}

}
