package data;

public class Course {
	
	private String cIdentifier, cName, university;
	
	/**
	 * 
	 * @param cIdentifier
	 * @param cName
	 * @param university
	 */
	public Course(String cIdentifier, String cName, String university){
		this.cIdentifier = cIdentifier;
		this.cName = cName;
		this.university = university;
	}
	
	/**
	 * 
	 * @return cIndentifier
	 */
	public String getCIdentifier(){
		return cIdentifier;
	}
	
	/**
	 * 
	 * @param cIndentifier
	 * 						the cIndenifier to be set
	 */
	public void setCIdentifier(String cIndentifier){
		this.cIdentifier = cIndentifier;
	}
	
	/**
	 * 
	 * @return cName
	 */
	public String getCName(){
		return cName;
	}
	
	/**
	 * 
	 * @param cName
	 * 				the cName to be set
	 */
	public void setCName(String cName){
		this.cName = cName;
	}
	
	/**
	 * 
	 * @return university
	 */
	public String getUniversity(){
		return university;
	}
	
	/**
	 * 
	 * @param university
	 * 					the university to be set
	 */
	public void setUniverisity(String university){
		this.university = university;
	}
	
	// TODO: Hashcode, equals, toString
}
