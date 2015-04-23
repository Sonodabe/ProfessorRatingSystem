package database;

import java.util.Collection;

public class SQLStatements {
	private static final char COMMA = ',';
	private static final char QUOTE = '\'';
	private static final String INSERT = "INSERT INTO %s (%s) VALUES (%s)";

	public static String insert(String table, Collection<String> attributes,
			Collection<Object> values) {

		if (attributes == null || values == null
				|| attributes.size() != values.size() || attributes.isEmpty()) {
			return null;
		}

		StringBuilder attributeString = new StringBuilder();
		StringBuilder valueString = new StringBuilder();

		for (String att : attributes) {
			attributeString.append(COMMA);
			attributeString.append(att);
		}

		for (Object val : values) {
			valueString.append(COMMA);

			if (val.getClass() == String.class) {
				valueString.append(QUOTE);
			}

			valueString.append(val.toString());

			if (val.getClass() == String.class) {
				valueString.append(QUOTE);
			}
		}

		attributeString.deleteCharAt(0);
		valueString.deleteCharAt(0);

		return String.format(INSERT, table, attributeString.toString(),
				valueString.toString());
	}
}