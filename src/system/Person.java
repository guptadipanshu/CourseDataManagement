package system;

public class Person {
	private final String UDID;
	private final String name;
	private final Address address;
	private final String number;
	
	public Person(final String id, final String name, final String address, final String number){
		this.UDID = id;
		this.name = name;
		this.address = new Address(address);
		this.number = number;
	}


	/**
	 * gets the unique id associated with a student
	 * @return the UDID associated with a student
	 */
	public String getID() {
		return this.UDID;
	}
	/**
	 * Get the name of the student
	 * @return the name of the student
	 */
	public String getName(){
		return this.name;
	}
	
	public String getNumber(){
		return this.number;
	}
	
	public Address getAddress(){
		return this.address;
	}
}
