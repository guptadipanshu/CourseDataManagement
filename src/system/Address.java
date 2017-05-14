package system;
/**
 * Helper class to store and retreive the address 
 * @author dipanshugupta
 *
 */
public class Address {
	
	private String houseNumber;
	private String streetName;
	private String zipCode;
	
	public Address(final String address) {
		String[] addr = address.split(" ");
		this.houseNumber = addr[0];
		this.streetName = addr[1];
		for(int i=2;i<addr.length-1;i++) {
			this.streetName += " "+addr[i];
		}
		this.zipCode = addr[addr.length-1];
	}
	
	/**
	 * get house number
	 * @return house number
	 */
	public String getHouseNumber(){
		return houseNumber;
	}
	/**
	 * get the street name
	 * @return the street name
	 */
	public String getStreetName(){
		return streetName;
	}
	
	/**
	 * get the zip code
	 * @return zipcode
	 */
	public String getZipCode(){
		return zipCode;
	}
}
