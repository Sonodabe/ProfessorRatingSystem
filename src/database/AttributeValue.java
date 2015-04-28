package database;

import java.util.ArrayList;
import utilities.ErrorUtilities;

public class AttributeValue {
	public static final int EQUAL = 0;
	public static final int NOT_EQUAL = 1;

	public static final int GREATER = 2;
	public static final int GREATER_OR_EQUAL = 3;

	public static final int LESS = 4;
	public static final int LESS_OR_EQUAL = 5;

	public static final int LIKE = 6;

	public static final int JOIN = 7;
	
	private static final String COMMA = ", ";
	private static final String AND = " AND ";

	public final String attribute;
	public final Object value;
	public final int comparison;

	public AttributeValue(String attribute, Object value, int comparison) {
		if (attribute == null) {
			throw new IllegalArgumentException(ErrorUtilities.EX_ATTRIBUTE_NULL);
		}

		if (comparison == JOIN && value == null) {
			throw new IllegalArgumentException(ErrorUtilities.EX_ATTRIBUTE_NULL);
		}

		this.attribute = attribute;
		this.value = value;
		this.comparison = comparison;
	}

	public AttributeValue(String attribute, Object value) {
		this(attribute, value, EQUAL);
	}

	public String toString() {
		if (comparison == JOIN) {
			return String.format("%s = %s", attribute, value);
		}

		StringBuilder ret = new StringBuilder();

		ret.append(attribute);
		ret.append(" ");

		switch (comparison) {
		case EQUAL:
			ret.append(value == null ? "IS" : "=");
			break;

		case NOT_EQUAL:
			ret.append(value == null ? "IS NOT" : "<>");
			break;

		case GREATER:
			ret.append(">");
			break;

		case GREATER_OR_EQUAL:
			ret.append(">=");
			break;

		case LESS:
			ret.append("<");
			break;

		case LESS_OR_EQUAL:
			ret.append("<=");
			break;

		case LIKE:
			ret.append("LIKE");
			break;
		}

		if (value == null) {
			ret.append(" NULL");
		} else {
			ret.append(" ?");
		}

		return ret.toString();
	}

	public static String list(ArrayList<AttributeValue> arr, String delimeter) {
		if (arr == null || arr.isEmpty())
			return "";

		StringBuilder ret = new StringBuilder();

		ret.append(" ");
		ret.append(arr.get(0).toString());

		for (int i = 1; i < arr.size(); i++) {
			ret.append(delimeter);
			ret.append(arr.get(i).toString());

		}

		ret.append(" ");

		return ret.toString();
	}

	public static String where(ArrayList<AttributeValue> arr) {
		return String.format(" WHERE %s", list(arr, AND));
	}

	public static String set(ArrayList<AttributeValue> arr) {
		return String.format(" SET %s", list(arr, COMMA));
	}
}
