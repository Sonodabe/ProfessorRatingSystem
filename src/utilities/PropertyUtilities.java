package utilities;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtilities {
	private static final String PROPERTIES_FILE_NAME = "config.properties";
	
	private static final String _db_name = "Database.name";
	private static String databaseName;
	
	static {
		try {
		Properties props = new Properties();
		
		InputStream is = PropertyUtilities.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
		
		if (is != null) {
			props.load(is);
			databaseName = props.getProperty(_db_name);
			
		} 
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(ErrorUtilities.EX_NOINPUT);
		}
	}
	
	public static String getDatabaseName() {
		return databaseName;
	}
}
