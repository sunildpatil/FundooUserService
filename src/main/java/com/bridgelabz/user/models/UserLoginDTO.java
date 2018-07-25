package com.bridgelabz.user.models;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginDTO {

	@Email(message="PLEASE PROVIDE VALID EMAIL ID")
	@Column(unique=true)
	@NotNull(message="Please Enter Valid Email")
	@NotEmpty(message="Email should not be Empty")
	private String email;

	@NotNull(message="Please Enter Valid Password")
	@Size(min=6, message="Password Must Contain min 6 characters")
	@NotEmpty(message="Password should not be Empty")
	private String password;

	public UserLoginDTO() {
		
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

	@Override
	public String toString() {
		return "UserDTO [email=" + email + ", password=" + password + "]";
	}
}
