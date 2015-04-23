package graphics;

import javax.swing.*;

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

	public PRSFrame(boolean admin) {
		super("Profressor Rating System");
		isAdmin = admin;
		setup();
	}

	/**
	 * Used to initialize all of the settings for a PRSFrame
	 */
	private void setup() {
		if (isAdmin) {
			adminTabs = new JTabbedPane();
			sm = new StudentManager();
			pm = new ProfessorManager();
			cm = new CourseManager();
			adminTabs.addTab("Students", sm);
			adminTabs.addTab("Professors", pm);
			adminTabs.addTab("Courses", cm);
			add(adminTabs);
		}
		else {
			studentTabs = new JTabbedPane();
			sc = new StudentCreator();
			studentTabs.addTab("Create Account", sc);
			rt = new ReviewTab();
			studentTabs.add("Compose Review", rt);
			add(studentTabs);
		}
	}

}
