package com.bridgelabz.user.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelabz.user.utility.ResponseHelper;
import com.bridgelabz.user.utility.TokenHelper;

@Configuration
public class ApplicationConfig {

	@Bean
	public TokenHelper tokenConfig() {

		return new TokenHelper();
	}

	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ResponseHelper responseHelper() {

		return new ResponseHelper();
	}	
}