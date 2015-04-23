package database;

import java.util.ArrayList;

public class SQLDatabaseProxy {
	
	public static boolean insert(String table, ArrayList<String> attributes, ArrayList<Object> values) {
		String sqlStatement = SQLStatements.insert(table, attributes, values);
		
		// TODO execute los queros
		
		return false;
	}
}
