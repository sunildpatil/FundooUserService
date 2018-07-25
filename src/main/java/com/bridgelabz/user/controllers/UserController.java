package com.bridgelabz.user.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.user.models.LoginResponseDTO;
import com.bridgelabz.user.models.Response;
import com.bridgelabz.user.models.UserLoginDTO;
import com.bridgelabz.user.models.UserRegistrationDTO;
import com.bridgelabz.user.services.UserService;
import com.bridgelabz.user.utility.ResponseHelper;

@RestController
@RequestMapping(value="/user")
@PropertySource("classpath:error.properties")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ResponseHelper responseHelper;
	
	@Autowired
    private Environment environment;
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ResponseEntity<Response> register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) throws Exception {
		
		userService.register(userRegistrationDTO);
		
		Response response = responseHelper.responseStatus(Integer.parseInt(environment.getProperty("statusexceptioncode")), environment.getProperty("registrationsuccessmsg"));
			 				
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/registeractivation/{token}", method=RequestMethod.GET)
	public ResponseEntity<Response> registerActivation(@PathVariable String token) throws Exception {
		
		userService.registerVerification(token);
		
		Response response = responseHelper.responseStatus(Integer.parseInt(environment.getProperty("statussuccesscode")), environment.getProperty("activationsuccessmsg"));
	
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/*@RequestMapping(value="/updateuser", method=RequestMethod.PUT)
	public ResponseEntity<Response> updateUser(@ModelAttribute("userRegistrationDTO") UserRegistrationDTO userUpdateDTO) throws Exception {

		userService.updateUser(userUpdateDTO);
		
		Response response = responseHelper.responseStatus(Integer.parseInt(environment.getProperty("statussuccesscode")), environment.getProperty("updationsuccessmsg"));
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteuser/{email}", method=RequestMethod.DELETE)
	public ResponseEntity<Response> deleteUser(@PathVariable String email) throws Exception {
		
		userService.deleteUser(email);
		
		Response response = responseHelper.responseStatus(Integer.parseInt(environment.getProperty("statussuccesscode")), environment.getProperty("deletesuccessmsg"));
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}*/

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) throws Exception {

		String resultToken = userService.login(userLoginDTO);
		
		LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
		loginResponseDTO.setCode(Integer.parseInt(environment.getProperty("statussuccesscode")));
		loginResponseDTO.setMessage(environment.getProperty("loginsuccess"));
		loginResponseDTO.setToken(resultToken);
							
		return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/forgotpassword", method=RequestMethod.POST)
	public ResponseEntity<Response> forgotPassword(@RequestParam String email) throws Exception {
		
		userService.forgotPassword(email);
				
		Response response = responseHelper.responseStatus(Integer.parseInt(environment.getProperty("statussuccesscode")), environment.getProperty("emailsentmsg"));
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/setnewpassword/{token}", method=RequestMethod.POST)
	public ResponseEntity<Response> setNewPassword(@PathVariable String token, @RequestParam String password) throws Exception {
		
		userService.setNewPassword(token, password);
		
		Response response = responseHelper.responseStatus(Integer.parseInt(environment.getProperty("statussuccesscode")), environment.getProperty("passwordchangedmsg"));
			
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}