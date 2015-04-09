package data;

import java.util.Comparator;

public class Student {
	private String name;
	private int id;
	private int major;

	private static Comparator<Student> idComparator;
	private static Comparator<Student> majorComparator;

	public Student(String name, int id, int major) {
		this.name = new String(name);
		this.id = id;
		this.major = major;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getMajor() {
		return major;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + major;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (id != other.id)
			return false;
		if (major != other.major)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Student <%s, %d, %d>", name, id, major);
	}

	public static Comparator<Student> getIdComparator() {
		if (idComparator == null) {
			idComparator = new Comparator<Student>() {

				@Override
				public int compare(Student s1, Student s2) {
					return s1.id - s2.id;
				}

			};
		}

		return idComparator;
	}

	public static Comparator<Student> getMajorComparator() {
		if (majorComparator == null) {
			majorComparator = new Comparator<Student>() {

				@Override
				public int compare(Student s1, Student s2) {
					return s1.major - s2.major;
				}

			};
		}

		return majorComparator;
	}
}
