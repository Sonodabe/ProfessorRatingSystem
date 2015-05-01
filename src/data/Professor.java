package data;

public class Professor {

	private String name, bio, researchArea;
	private int id, yearsWorked;

	/**
	 * @param name
	 * @param bio
	 * @param id
	 * @param yearsWorked
	 */
	public Professor(String name, String bio, int id,
			int yearsWorked, String researchArea) {
		this.name = name;
		this.bio = bio;
		this.id = id;
		this.yearsWorked = yearsWorked;
		this.researchArea = researchArea;
	}

	/**
	 * @param s
	 */
	public Professor(String[] s) {
		id = Integer.parseInt(s[0]);
		name = s[1];
		researchArea = s[2];
		bio = s[3];
		yearsWorked = Integer.parseInt(s[4]);

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

	public String getResearchArea() {
		return researchArea;
	}

	public void setResearchArea(String researchArea) {
		this.researchArea = researchArea;
	}

	// TODO: Hashcode, equals, toString
}
