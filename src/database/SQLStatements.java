package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import utilities.ErrorUtilities;

public class SQLStatements {
	// Used Symbols
	private static final char COMMA = ',';
	private static final char QUESTION_MARK = '?';

	// Caches insert statements so they don't have to be generated
	// multiple
	// times
	private static final HashMap<String, String> insertStatements;

	// SQL statments
	private static final String INSERT = "INSERT INTO %s (%s) VALUES (%s)";
	private static final String SELECT = "SELECT %s FROM %s";
	private static final String DELETE = "DELETE FROM %s";
	private static final String UPDATE = "UPDATE %s";

	// Initializing variables
	static {
		insertStatements = new HashMap<String, String>();
	}

	/**
	 * Generates the prepared statement for an insert call
	 * 
	 * @param dbc
	 *            The connection to which we are inserting
	 * @param table
	 *            The name of the table into which the statement will insert
	 * @param attributes
	 *            The list of attributes in the table
	 * @param values
	 *            The list of values in the record
	 * @return A prepared statment populated with the insert logic and the
	 *         values of the new record. <code>null</code> if there is an error
	 *         in creating the PreparedStatement.
	 */
	public static PreparedStatement insert(Connection dbc, String table,
			ArrayList<String> attributes, ArrayList<Object> values) {

		// Don't process if any of these values aren't set
		if (dbc == null || attributes == null || values == null
				|| attributes.size() != values.size() || attributes.isEmpty()) {
			return null;
		}

		try {
			// Check if we need to generate the statement
			if (!insertStatements.containsKey(table)) {
				createStatement(table, attributes);
			}

			String statement = insertStatements.get(table);
			PreparedStatement pstmt = dbc.prepareStatement(statement);
			setAttributes(pstmt, values);
			return pstmt;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets attributes from every record from a specified table
	 * 
	 * @param dbc
	 *            The connection from which we are selecting
	 * @param attributes
	 *            The attributes that will be returned from the SQL call
	 * @param table
	 *            The table from which we are selecting
	 * @return The prepared statement that will return all records with given
	 *         attributes
	 */
	public static PreparedStatement select(Connection dbc,
			ArrayList<String> attributes, String table, String groupBy,
			ArrayList<AttributeValue> filter) {
		if (dbc == null || attributes == null || attributes.isEmpty()) {
			return null;
		}

		try {
			// Build the prepared statement string
			StringBuilder attributeString = new StringBuilder();

			for (String att : attributes) {
				attributeString.append(COMMA);
				attributeString.append(att);
			}

			attributeString.deleteCharAt(0);

			String statement = String.format(SELECT,
					attributeString.toString(), table);

			if (filter != null && !filter.isEmpty()) {
				statement += AttributeValue.where(filter);
			}
			
			if (groupBy != null && !groupBy.trim().empty()) {
				statement += " GROUP BY" + groupBy;
			}

			PreparedStatement pstmt = dbc.prepareStatement(statement);

			if (filter != null && !filter.isEmpty()) {
				ArrayList<Object> vals = new ArrayList<Object>();

				for (AttributeValue av : filter) {
					if (av.comparison != AttributeValue.JOIN
							&& av.value != null) {
						vals.add(av.value);
					}
				}

				setAttributes(pstmt, vals);
			}

			return pstmt;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static PreparedStatement delete(Connection dbc, String table,
			ArrayList<AttributeValue> filter) {

		if (dbc == null || table == null) {
			return null;
		}

		try {

			String statement = String.format(DELETE, table);

			if (filter != null && !filter.isEmpty()) {
				statement += AttributeValue.where(filter);
			}

			PreparedStatement pstmt = dbc.prepareStatement(statement);

			if (filter != null && !filter.isEmpty()) {
				ArrayList<Object> vals = new ArrayList<Object>();

				for (AttributeValue av : filter) {
					if (av.comparison != AttributeValue.JOIN
							&& av.value != null) {
						vals.add(av.value);
					}
				}

				setAttributes(pstmt, vals);
			}

			return pstmt;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static PreparedStatement update(Connection dbc, String table,
			ArrayList<AttributeValue> atts, ArrayList<AttributeValue> filter) {

		if (dbc == null || table == null || atts == null || filter == null
				|| atts.isEmpty() || filter.isEmpty()) {
			return null;
		}

		try {

			String statement = String.format(UPDATE, table);

			statement += AttributeValue.set(atts);
			statement += AttributeValue.where(filter);

			PreparedStatement pstmt = dbc.prepareStatement(statement);

			ArrayList<Object> avals = new ArrayList<Object>();
			ArrayList<Object> fvals = new ArrayList<Object>();

			for (AttributeValue av : atts) {
				if (av.comparison != AttributeValue.JOIN && av.value != null) {
					avals.add(av.value);

				}
			}
			
			for (AttributeValue av : filter) {
				if (av.comparison != AttributeValue.JOIN && av.value != null) {
					fvals.add(av.value);

				}
			}

			int numAttributes = setAttributes(pstmt, avals);
			setAttributes(pstmt, fvals, numAttributes);

			return pstmt;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Generates the prepared statement string with a given set of attributes
	 * 
	 * @param table
	 *            The table name under which this statement will be stored in
	 *            the hash table
	 * @param attributes
	 *            The attributes that will be added into the statement
	 */
	private static void createStatement(String table,
			ArrayList<String> attributes) {

		// Will hold the list of attributes
		StringBuilder attributeString = new StringBuilder();
		StringBuilder valueString = new StringBuilder();

		// Make a list of the attributes
		for (String att : attributes) {
			attributeString.append(COMMA);
			attributeString.append(att);
		}

		// Make a list of question marks (used for the prepared
		// statement)
		for (int i = 0; i < attributes.size(); i++) {
			valueString.append(COMMA);
			valueString.append(QUESTION_MARK);
		}

		// Remove the first commmas
		attributeString.deleteCharAt(0);
		valueString.deleteCharAt(0);

		String statement = String.format(INSERT, table,
				attributeString.toString(), valueString.toString());

		// Insert the statment into the hashtable
		insertStatements.put(table, statement);
	}

	/**
	 * Inserts a given set of values into a prepared statement
	 * 
	 * @param pstmt
	 *            The statement into which the values are being inserted
	 * @param values
	 *            The list of object that will be inserted into the prepared
	 *            statement
	 * @throws SQLException
	 *             If there are issues inserting the values into the prepared
	 *             statement
	 */
	private static int setAttributes(PreparedStatement pstmt,
			ArrayList<Object> values, int index) throws SQLException {

		for (Object val : values) {
			if (val == null) {
				pstmt.setNull(++index, Types.NULL);
				continue;
			}

			// Gets the type of the value
			Class<? extends Object> c = val.getClass();

			if (c == String.class) {
				pstmt.setString(++index, (String) val);
				continue;
			}

			if (c == Integer.class) {
				pstmt.setInt(++index, (Integer) val);
				continue;
			}

			if (c == Double.class) {
				pstmt.setDouble(++index, (Double) val);
				continue;
			}

			// Probably not good
			pstmt.setObject(++index, val);
			System.err.printf(ErrorUtilities.EX_VALNOTCAUGHT, val.toString(),
					index);

		}

		return index;
	}

	private static int setAttributes(PreparedStatement pstmt,
			ArrayList<Object> vals) throws SQLException {
		return setAttributes(pstmt, vals, 0);
	}
}