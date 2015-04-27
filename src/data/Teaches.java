package data;

public class Teaches {
	
	private String cNumber, pid;
	
	/**
	 * 
	 * @param cNumber
	 * @param pid
	 */
	public Teaches(String cNumber, String pid){
		this.cNumber = cNumber;
		this.pid = pid;
	}
	
	/**
	 * 
	 * @return cNumber
	 */
	public String getCNumber(){
		return cNumber;
	}
	
	/**
	 * 
	 * @param cNumber
	 * 					the cNumber to be set
	 */
	public void setCNumber(String cNumber){
		this.cNumber = cNumber;
	}
	
	/**
	 * 
	 * @return pid
	 */
	public String getPid(){
		return pid;
	}
	
	/**
	 * 
	 * @param pid
	 * 				the pid to be set
	 */
	public void setPid(String pid){
		this.pid = pid;
	}
	
	// TODO: Hashcode, equals, toString
}
