package com.bridgelabz.user.models;

public class LoginResponseDTO {

	private int code;
	private String message;
	private String token;
	
	public LoginResponseDTO() {

	}

	public LoginResponseDTO(int code, String message, String token) {
		super();
		this.code = code;
		this.message = message;
		this.token = token;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginResponseDTO [code=" + code + ", message=" + message + ", token=" + token + "]";
	}
}
