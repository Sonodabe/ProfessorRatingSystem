package database;

import java.sql.*;
import java.util.ArrayList;
import utilities.*;

public class SQLDatabaseProxy {
	private static Connection dbc = null;

	static {
		try {
			// load the sqlite-JDBC driver using the current class
			// loader
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

	public static boolean insert(String table, ArrayList<String> attributes,
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
			ArrayList<String> attributes) {
		return select(table, attributes, null);
	}

	public static ArrayList<String[]> select(String table,
			ArrayList<String> attributes, ArrayList<AttributeValue> filter) {

		PreparedStatement pstmt = SQLStatements.select(dbc, attributes, table,
				filter);

		try {
			ArrayList<String[]> results = new ArrayList<String[]>();

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String[] temp = new String[attributes.size()];

				for (int i = 0; i < temp.length; i++) {
					Object cur = rs.getObject(i + 1);

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

	public static int delete(String table, ArrayList<AttributeValue> filter) {
		try {
			PreparedStatement pstmt = SQLStatements.delete(dbc, table, filter);

			int numDeleted = pstmt.executeUpdate();

			return numDeleted;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static int update(String table, ArrayList<AttributeValue> atts,
			ArrayList<AttributeValue> filter) {
		try {
			PreparedStatement pstmt = SQLStatements.update(dbc, table, atts, filter);

			int numUpdated = pstmt.executeUpdate();

			return numUpdated;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
