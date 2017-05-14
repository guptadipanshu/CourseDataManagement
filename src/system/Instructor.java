package system;

/**
 * class to store instructor information
 * @author dipanshugupta
 *
 */
public class Instructor extends Person {

	public Instructor(String id, String name, String address, String number) {
		super(id,name,address,number);
	}
	
	/**
	 * get the uniqueID of the instructor
	 * @return instructors UDID
	 */
	public String getID() {
		return super.getID();
	}
	
	/**
	 * get the instructor name
	 * @return instructor name
	 */
	public String getName(){
		//System.out.println("test "+address.getHouseNumber()+" "+address.getStreetName()+" "+address.getZipCode());
		return super.getName();
	}
}
