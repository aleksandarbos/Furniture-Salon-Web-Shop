package beans;

import java.io.Serializable;

public class Salon implements Serializable{

	private String giroAccount;
	private String registrationNumber;
	private String PIBnumber;
	private String name;
	private String address;
	private String cellNumber;
	private String email;
	private String websiteAddress;
	
	public Salon() {}

	public Salon(String giroAccount, String registrationNumber,
			String PIBnumber, String name, String address, String cellNumber,
			String email, String websiteAddress) {
		super();
		this.giroAccount = giroAccount;
		this.registrationNumber = registrationNumber;
		this.PIBnumber = PIBnumber;
		this.name = name;
		this.address = address;
		this.cellNumber = cellNumber;
		this.email = email;
		this.websiteAddress = websiteAddress;
	}

	public String getGiroAccount() {
		return giroAccount;
	}

	public void setGiroAccount(String giroAccount) {
		this.giroAccount = giroAccount;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getPIBnumber() {
		return PIBnumber;
	}

	public void setPIBnumber(String pIBnumber) {
		PIBnumber = pIBnumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsiteAddress() {
		return websiteAddress;
	}

	public void setWebsiteAddress(String websiteAddress) {
		this.websiteAddress = websiteAddress;
	}
	
	

}
