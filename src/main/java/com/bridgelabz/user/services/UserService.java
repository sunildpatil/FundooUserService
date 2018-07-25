package com.bridgelabz.user.services;

import com.bridgelabz.user.exceptions.TokenException;
import com.bridgelabz.user.exceptions.LoginException;
import com.bridgelabz.user.exceptions.RegistraionException;
import com.bridgelabz.user.models.UserLoginDTO;
import com.bridgelabz.user.models.UserRegistrationDTO;

public interface UserService {

	public void register(UserRegistrationDTO userRegistrationDTO) throws RegistraionException;
	/*public void updateUser(UserRegistrationDTO user) throws RegistraionException;
	public void deleteUser(String email) throws RegistraionException;*/
	public String login(UserLoginDTO userLoginDTO) throws LoginException;
	public void registerVerification(String token) throws RegistraionException, TokenException;
	public void forgotPassword(String email) throws  TokenException;
	public void setNewPassword(String token, String password) throws TokenException;	
}