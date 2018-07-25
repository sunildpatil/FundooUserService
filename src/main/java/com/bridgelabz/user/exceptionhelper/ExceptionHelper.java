package com.bridgelabz.user.exceptionhelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.user.exceptions.TokenException;
import com.bridgelabz.user.exceptions.LoginException;
import com.bridgelabz.user.exceptions.RegistraionException;
import com.bridgelabz.user.models.Response;
import com.bridgelabz.user.utility.ResponseHelper;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
@PropertySource("classpath:error.properties")
public class ExceptionHelper {
	
	@Autowired
    private Environment environment;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Response> handleException(Exception exception) {
		
		Response response = responseHelper.responseStatus(Integer.parseInt(environment.getProperty("statusexceptioncode")), environment.getProperty("errormessage"));
		logger.error("Error : " +exception.getMessage(), exception);
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	} 
	
	@Autowired
	private ResponseHelper responseHelper;
	
	@ExceptionHandler(value = ExpiredJwtException.class)
	public ResponseEntity<Response> handleExpiredJwtException(Exception exception) {	
		
		Response response = responseHelper.responseStatus(Integer.parseInt(environment.getProperty("statusexpiredjwtcode")), exception.getMessage());
		logger.info(" Error : " +exception.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	} 
	
	@ExceptionHandler(value = RegistraionException.class)
	public ResponseEntity<Response> handleRegistrationException(RegistraionException exception) {
		
		Response response = responseHelper.responseStatus(Integer.parseInt(environment.getProperty("statusregistratingcode")), exception.getMessage());
		logger.info(exception.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = LoginException.class)
	public ResponseEntity<Response> handleLoginException(LoginException exception) {
		
		Response response = responseHelper.responseStatus(Integer.parseInt(environment.getProperty("statuslogincode")), exception.getMessage());
		logger.info(exception.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = TokenException.class)
	public ResponseEntity<Response> handleForgotPasswordException(TokenException exception) {
		
		Response response = responseHelper.responseStatus(Integer.parseInt(environment.getProperty("statustokencode")), exception.getMessage());
		logger.info(exception.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}