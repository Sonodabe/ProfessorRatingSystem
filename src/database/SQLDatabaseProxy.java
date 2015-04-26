package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
			String connectionString = String.format("jdbc:sqlite:%s",
					databaseName);
			dbc = DriverManager.getConnection(connectionString);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(ErrorUtilities.EX_NOINPUT);
		}
	}

	public static boolean insert(String table, Collection<String> attributes,
			ArrayList<Object> values) {

		PreparedStatement pstmt = SQLStatements.insert(dbc, table, attributes,
				values);

		try {
			int numRows = pstmt.executeUpdate();
			return numRows != 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList<String[]> select(String table,
			Collection<String> attributes) {

		PreparedStatement pstmt = SQLStatements.select(dbc, attributes, table);

		try {
			ArrayList<String[]> results = new ArrayList<String[]>();

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String[] temp = new String[attributes.size()];

				for (int i = 0; i < temp.length; i++) {
					Object cur = rs.getObject(i);

					temp[i] = (cur == null) ? null : cur.toString();
				}

				results.add(temp);
			}

			return results;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
