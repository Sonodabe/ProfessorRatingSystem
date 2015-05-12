package data;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Review {

	private int sid, year, cid, engagement, fairness, difficultyWork,
			easeLearning, teachingStyle, pid;
	private String semester, comments;

	/**
	 * @param sid
	 * @param year
	 * @param cid
	 * @param engagemnt
	 * @param fairness
	 * @param difficultyWork
	 * @param easeLearning
	 * @param teachingStyle
	 * @param pid
	 * @param semester
	 * @param comments
	 */
	public Review(int sid, int year, int cid, int engagement,
			int fairness, int difficultyWork, int easeLearning,
			int teachingStyle, int pid, String semester,
			String comments) {
		this.sid = sid;
		this.year = year;
		this.cid = cid;
		this.engagement = engagement;
		this.fairness = fairness;
		this.difficultyWork = difficultyWork;
		this.easeLearning = easeLearning;
		this.teachingStyle = teachingStyle;
		this.pid = pid;
		this.semester = semester;
		this.comments = comments;
	}

	/**
	 * @param strings
	 */
	public Review(String[] s) {
		sid = Integer.parseInt(s[0]);
		pid = Integer.parseInt(s[1]);
		cid = Integer.parseInt(s[2]);
		year = Integer.parseInt(s[3]);
		DecimalFormat df = new DecimalFormat("#");
		df.setRoundingMode(RoundingMode.HALF_UP);
		semester = s[4];
		engagement = s[5] == null ? 0 : Integer.parseInt(df
				.format(Double.parseDouble(s[5])));
		fairness = s[6] == null ? 0 : Integer.parseInt(df
				.format(Double.parseDouble(s[6])));
		difficultyWork = s[7] == null ? 0 : Integer.parseInt(df
				.format(Double.parseDouble(s[7])));
		easeLearning = s[8] == null ? 0 : Integer.parseInt(df
				.format(Double.parseDouble(s[8])));
		teachingStyle = s[9] == null ? 0 : Integer.parseInt(df
				.format(Double.parseDouble(s[9])));
		if (s.length == 11)
			comments = s[10];

	}

	/**
	 * 
	 * @return the sid
	 */
	public int getSid() {
		return sid;
	}

	/**
	 * @param sid
	 *            the sid to set
	 */
	public void setSid(int sid) {
		this.sid = sid;
	}

	/**
	 * 
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * 
	 * @return cid
	 */
	public int getCid() {
		return cid;
	}

	/**
	 * 
	 * @param cid
	 *            the cid to set
	 */
	public void setCid(int cid) {
		this.cid = cid;
	}

	/**
	 * 
	 * @return engagement
	 */
	public int getEngagement() {
		return engagement;
	}

	/**
	 * 
	 * @param engagement
	 *            the engagement to set
	 */
	public void setEngagement(int engagement) {
		this.engagement = engagement;
	}

	/**
	 * 
	 * @return fairness
	 */
	public int getFairness() {
		return fairness;
	}

	/**
	 * 
	 * @param fairness
	 *            the fairness to set
	 */
	public void setFairness(int fairness) {
		this.fairness = fairness;
	}

	/**
	 * 
	 * @return difficultyWork
	 */
	public int getDifficultyWork() {
		return difficultyWork;
	}

	/**
	 * 
	 * @param difficultyWork
	 *            the difficultyWork to set
	 */
	public void setDifficultyWork(int difficultyWork) {
		this.difficultyWork = difficultyWork;
	}

	/**
	 * 
	 * @return easeLearning
	 */
	public int getEaseLearning() {
		return easeLearning;
	}

	/**
	 * 
	 * @param easeLearning
	 *            the easeLearning to set
	 */
	public void setEaseLearning(int easeLearning) {
		this.easeLearning = easeLearning;
	}

	/**
	 * 
	 * @return teachingStyle
	 */
	public int getTeachingStyle() {
		return teachingStyle;
	}

	/**
	 * 
	 * @param teachingStyle
	 *            the teachingStyle to set
	 */
	public void setTeachingStyle(int teachingStyle) {
		this.teachingStyle = teachingStyle;
	}

	/**
	 * @return the pid
	 */
	public int getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(int pid) {
		this.pid = pid;
	}

	/**
	 * @param semester
	 *            the semester to set
	 */
	public void setSemester(String semester) {
		this.semester = semester;
	}

	/**
	 * 
	 * @return semester
	 */
	public String getSemester() {
		return semester;
	}

	/**
	 * 
	 * @param semester
	 *            the semester to set
	 */
	public void setSemster(String semester) {
		this.semester = semester;
	}

	/**
	 * 
	 * @return semester
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * 
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
}
