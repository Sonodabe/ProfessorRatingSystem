/**
 * 
 */
package database;

import java.util.ArrayList;
import utilities.ErrorUtilities;

/**
 * @author Doug Blase
 *
 */
public class AttributeAttribute {
	public static final int EQUAL = 0;
	public static final int NOT_EQUAL = 1;

	public static final int GREATER = 2;
	public static final int GREATER_OR_EQUAL = 3;

	public static final int LESS = 4;
	public static final int LESS_OR_EQUAL = 5;

	public static final int LIKE = 6;

	public final String attribute1, attribute2;
	public final int comparison;

	public AttributeAttribute(String attribute, String attr2,
			int comparison) {
		if (attribute == null)
			throw new IllegalArgumentException(
					ErrorUtilities.EX_ATTRIBUTE_NULL);

		this.attribute1 = attribute;
		this.comparison = comparison;
		this.attribute2 = attr2;
	}

	public AttributeAttribute(String attribute, String attr2) {
		this(attribute, attr2, EQUAL);
	}

	public String toString() {
		StringBuilder ret = new StringBuilder();

		ret.append(attribute1);
		ret.append(" ");

		switch (comparison) {
		case EQUAL:
			ret.append(attribute2 == null ? "IS" : "=");
			break;

		case NOT_EQUAL:
			ret.append(attribute2 == null ? "IS NOT" : "<>");
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

		if (attribute2 == null) {
			ret.append(" NULL");
		}
		else {
			ret.append(" ?");
		}

		return ret.toString();
	}

	public static String where(ArrayList<AttributeAttribute> arr) {
		if (arr == null || arr.isEmpty())
			return "";

		StringBuilder ret = new StringBuilder();

		ret.append(" WHERE ");
		ret.append(arr.get(0).toString());

		for (int i = 1; i < arr.size(); i++) {
			ret.append(" AND ");
			ret.append(arr.get(i).toString());

		}

		ret.append(" ");

		return ret.toString();
	}
}
