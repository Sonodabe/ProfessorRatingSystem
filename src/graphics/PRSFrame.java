package graphics;

import javax.swing.*;

public class PRSFrame extends JFrame {

	public static boolean JDBC = false;

	private static final long serialVersionUID = 6762556961886566340L;

	private boolean isAdmin;
	private JTabbedPane adminTabs, studentTabs;

	private StudentManager sm;

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
			adminTabs.addTab("Students", sm);

			add(adminTabs);
		}
	}

}
