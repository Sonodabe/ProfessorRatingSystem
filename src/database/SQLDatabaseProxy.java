package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import utilities.ErrorUtilities;
import utilities.PropertyUtilities;

public class SQLDatabaseProxy {
	private static Connection dbc = null;

	static {
		try {
			// load the sqlite-JDBC driver using the current class loader
			Class.forName("org.sqlite.JDBC");

			// Set up connection
			String databaseName = PropertyUtilities.getDatabaseName();
			String connectionString = String.format("jdbc:sqlite:%s", databaseName);
			dbc = DriverManager.getConnection(connectionString);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(ErrorUtilities.EX_NOINPUT);
		}
	}

	public static boolean insert(String table, ArrayList<String> attributes,
			ArrayList<Object> values) {

		PreparedStatement pstmt = SQLStatements.insert(dbc, table, attributes,
				values);

		try {
			return pstmt.executeQuery().rowInserted();
		} catch (SQLException e) {
			return false;
		}
	}
}
