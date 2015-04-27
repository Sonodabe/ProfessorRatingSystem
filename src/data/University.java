package data;

public class University {
	
	private String uName;
	
	/**
	 * 
	 * @param uName
	 */
	public University(String uName){
		this.uName = uName;
	}
	
	/**
	 * 
	 * @return uName
	 */
	public String getUName(){
		return uName;
	}
	
	/**
	 * 
	 * @param uName
	 * 				the uName to be set
	 */
	public void setUName(String uName){
		this.uName = uName;
	}
	
	// TODO: Hashcode, equals, toString
}
