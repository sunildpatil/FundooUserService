package com.bridgelabz.user.models;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class  UserRegistrationDTO {
	
	@NotNull(message="Please Enter Valid Name")
	@NotEmpty(message="Name should not be Empty")
	private String name;
	
	@NotNull(message="Please Enter Valid Address") 
	@NotEmpty(message="Address should not be Empty")
	private String address;
	
	@Email(message="PLEASE PROVIDE VALID EMAIL ID")
	@Column(unique=true)
	@NotNull(message="Please Enter Valid Email")
	@NotEmpty(message="Email should not be Empty")
	private String email;
	
	@NotNull(message="Please Enter Valid Password")
	@Size(min=6, message="Password Must Contain min 6 characters")
	@NotEmpty(message="Password should not be Empty")
	private String password;
	
	@NotNull(message="Please Enter Valid Gender")
	@NotEmpty(message="Gender should not be Empty")
	private String gender;
	
	@NotNull(message="Please Enter Valid Contact Number")
	private long contactNumber;

	public UserRegistrationDTO() {
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return "UserRegistrationDTO [name=" + name + ", address=" + address + ", email=" + email + ", password="
				+ password + ", gender=" + gender + ", contactNumber=" + contactNumber + "]";
	}
}