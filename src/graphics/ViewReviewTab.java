package graphics;
import graphics.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import database.SQLDatabaseProxy;
/**
 * @author Doug Blase
 * @author Samantha Wolf
 *
 */
public class ViewReviewTab extends JPanel {

	private JTable dataTable;
	private DefaultTableModel model;
	private ViewReviewPane vrp;
	private String colNames[] = { "SID", "PID", "CID", "Year", "Semester", "Engagement", "Fairness", "Difficulty of Work", 
			"Ease of Learning", "Teaching Style", "Comments"};

	public ViewReviewTab() {
		setLayout(new GridLayout(2, 0));

		ArrayList<String> atts = new ArrayList<String>();
		atts.add("SID");
		atts.add("PID");
		atts.add("CID");
		atts.add("Year");
		atts.add("Semester");
		atts.add("Engagement");
		atts.add("Fairness");
		atts.add("DifficultyWork");
		atts.add("EaseLearning");
		atts.add("TeachingStyle");
		atts.add("Comments");


		ArrayList<String[]> updated = SQLDatabaseProxy.select("Review", atts);
		model = new DefaultTableModel(colNames, 0);
		dataTable = new JTable(model);

		for (int i = 0; i < updated.size(); i++) {
			addRow(updated.get(i));
		}

		dataTable.setName("ReviewView");
		JScrollPane scrollPane = new JScrollPane(dataTable);
		dataTable.setFillsViewportHeight(true);
		dataTable.setShowGrid(true);
		dataTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		dataTable.setGridColor(Color.BLACK);

		add(scrollPane);

		
		vrp = new ViewReviewPane(this);
		add(vrp);
		add(scrollPane);
	}
	
	private void addRow(String[] row) {

		Vector<Object> rowData = new Vector<Object>();
		rowData.add(row[0]);
		rowData.add(row[1]);
		rowData.add(row[2]);
		rowData.add(row[3]);
		rowData.add(row[4]);
		rowData.add(row[5]);
		rowData.add(row[6]);
		rowData.add(row[7]);
		rowData.add(row[8]);
		rowData.add(row[9]);
		rowData.add(row[10]);
		model.addRow(rowData);

	}
}
