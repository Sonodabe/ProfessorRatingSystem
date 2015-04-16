package data;

public class Teacher {

	private String name, bio;
	private int id, yearsWorked;

	/**
	 * @param name
	 * @param bio
	 * @param id
	 * @param yearsWorked
	 */
	public Teacher(String name, String bio, int id, int yearsWorked) {
		this.name = name;
		this.bio = bio;
		this.id = id;
		this.yearsWorked = yearsWorked;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the bio
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * @param bio
	 *            the bio to set
	 */
	public void setBio(String bio) {
		this.bio = bio;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the yearsWorked
	 */
	public int getYearsWorked() {
		return yearsWorked;
	}

	/**
	 * @param yearsWorked
	 *            the yearsWorked to set
	 */
	public void setYearsWorked(int yearsWorked) {
		this.yearsWorked = yearsWorked;
	}

	// TODO: Hashcode, equals, toString
}
