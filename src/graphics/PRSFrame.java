package graphics;

import java.util.ArrayList;
import javax.swing.*;
import database.SQLDatabaseProxy;

public class PRSFrame extends JFrame {

	public static boolean JDBC = false;

	private static final long serialVersionUID = 6762556961886566340L;

	private boolean isAdmin;
	private JTabbedPane adminTabs, studentTabs;

	private StudentManager sm;
	private ProfessorManager pm;
	private CourseManager cm;

	private StudentCreator sc;
	private ReviewTab rt;
	private ViewReviewTab vrt;

	public PRSFrame(boolean admin) {
		super("Profressor Rating System");
		isAdmin = admin;
		setup();
	}

	/**
	 * Used to initialize all of the settings for a PRSFrame
	 */
	private void setup() {
		vrt = new ViewReviewTab();
		if (isAdmin) {
			adminTabs = new JTabbedPane();
			sm = new StudentManager();
			pm = new ProfessorManager();
			cm = new CourseManager();
			adminTabs.addTab("Students", sm);
			adminTabs.addTab("Professors", pm);
			adminTabs.addTab("Courses", cm);
			adminTabs.addTab("Reviews", vrt);
			add(adminTabs);
		}
		else {
			studentTabs = new JTabbedPane();
			sc = new StudentCreator();
			studentTabs.addTab("Create Account", sc);
			rt = new ReviewTab();
			studentTabs.add("Compose Review", rt);
			studentTabs.addTab("View Reviews", vrt);
			add(studentTabs);
		}
	}

	// TODO allow for a filter of some sort...
	public static void updateSelector(JComboBox<String> comboBox,
			String tableName, String fieldName) {
		ArrayList<String[]> records;

		ArrayList<String> atts = new ArrayList<String>();
		atts.add(fieldName);

		records = SQLDatabaseProxy.select(tableName, atts);

		comboBox.removeAllItems();

		for (String[] arr : records) {
			comboBox.addItem(arr[0]);
		}
	}

}
