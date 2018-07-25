package com.bridgelabz.user.services;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.user.exceptions.TokenException;
import com.bridgelabz.user.exceptions.LoginException;
import com.bridgelabz.user.exceptions.RegistraionException;
import com.bridgelabz.user.models.Email;
import com.bridgelabz.user.models.User;
import com.bridgelabz.user.models.UserLoginDTO;
import com.bridgelabz.user.models.UserRegistrationDTO;
import com.bridgelabz.user.repositories.UserRepository;
import com.bridgelabz.user.utility.TokenHelper;

@Transactional
@Service
@PropertySource("classpath:error.properties")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TokenHelper tokenHelper;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Value("${exchange}")
	private String exchange;

	@Value("${routingkey}")
	private String routingkey;

	@Value("${mailsender}")
	private String from;

	@Value("${hostmachine}")
	private String host;
	
	@Value("${issuerreg}")
	private String issuer;
	
	@Value("${registrationsubject}")
	private String registrationSubject;
	
	@Value("${time}")
	private String tokenExpiredTime;
	
	@Value("${issuerfpassword}")
	private String issuerForgotPassword;
	
	@Value("${fpasswordsubject}")
	private String fPasswordSubject;
	
	@Value("${registerlink}")
	private String registerLink;
	
	@Value("${forgotpasswordlink}")
	private String forgotpasswordlink;  
	
	@Value("${emailregistrationsubject}")
	private String emailregistrationsubject;
	
	@Value("${emailforgotpasswordubject}")
	private String emailforgotpasswordubject;
	
	@Value("${issuerlogin}")
	private String issuerLogin;
	
	@Value("${loginsubject}")
	private String loginSubject;
	
	@Autowired
    private Environment environment;

	@Override
	public void register(UserRegistrationDTO userRegistrationDTO) throws RegistraionException {

		User user = modelMapper.map(userRegistrationDTO, User.class);

		String email = user.getEmail();

		User userByEmail = userRepository.findByEmail(email);

		if (userByEmail != null) {

			throw new RegistraionException(environment.getProperty("regiderrormsg"));
		}

		user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
		user.setVerified(false);

		user = userRepository.save(user);

		String resultToken = tokenHelper.createJWT(Integer.toString(user.getId()), issuer, registrationSubject, Long.parseLong(tokenExpiredTime));

		String subject = emailregistrationsubject;
		String link = host + registerLink + resultToken;
		String body = link;
		String to = email;

		Email emailObj = new Email();
		emailObj.setTo(to);
		emailObj.setFrom(from);
		emailObj.setSubject(subject);
		emailObj.setBody(body);

		rabbitTemplate.convertAndSend(exchange, routingkey, emailObj);
	}

	@Override
	public void registerVerification(String token) throws RegistraionException, TokenException {

		int id = Integer.parseInt(tokenHelper.parseJWT(token));
		
		User userByID = userRepository.findById(id);

		if (userByID == null) {

			throw new RegistraionException(environment.getProperty("regactivationerrormsg"));
		}

		boolean verifiedFlag = userByID.getVerified();

		if (verifiedFlag) {
			
			throw new RegistraionException(environment.getProperty("regverifiedmsg"));
		}

		userByID.setVerified(true);
		
		userRepository.save(userByID);
	}

	/*@Override
	public void updateUser(UserRegistrationDTO userUpdateDTO) throws RegistraionException {

		User userByEmail = userRepository.findByEmail(userUpdateDTO.getEmail());

		if (userByEmail == null) {
			
			throw new RegistraionException(environment.getProperty("emailiderrormsg"));
		}

		userByEmail.setName(userUpdateDTO.getName());
		userByEmail.setAddress(userUpdateDTO.getAddress());
		userByEmail.setEmail(userUpdateDTO.getEmail());
		userByEmail.setPassword(userUpdateDTO.getPassword());
		userByEmail.setGender(userUpdateDTO.getGender());
		userByEmail.setContactNumber(userUpdateDTO.getContactNumber());

		userRepository.save(userByEmail);
	}

	@Override
	public void deleteUser(String email) throws RegistraionException {

		int result = userRepository.deleteByEmail(email);

		if (result == 0) {

			throw new RegistraionException(environment.getProperty("emailiderrormsg"));
		}
	}*/

	@Override
	public String login(UserLoginDTO userLoginDTO) throws LoginException {

		String email = userLoginDTO.getEmail();
		String password = userLoginDTO.getPassword();

		User userLogin = userRepository.findByEmail(email);

		if (userLogin == null) {

			throw new LoginException(environment.getProperty("emailiderrormsg"));
		}

		String dbPassword = userLogin.getPassword();

		boolean flag = passwordEncoder.matches(password, dbPassword);

		if (!flag) {

			throw new LoginException(environment.getProperty("passworderrormsg"));
		}

		boolean dbVerifiedFlag = userLogin.getVerified();

		if (!dbVerifiedFlag) {

			throw new LoginException(environment.getProperty("activationerrormsg"));
		}
				
		String resultToken = tokenHelper.createJWT(Integer.toString(userLogin.getId()), issuerLogin, loginSubject, Long.parseLong(tokenExpiredTime));
		System.out.println("Working "+resultToken);
		
		return resultToken;
	}

	@Override
	public void forgotPassword(String email) throws TokenException {

		User userByEmail = userRepository.findByEmail(email);

		if (userByEmail == null) {

			throw new TokenException(environment.getProperty("emailiderrormsg"));
		}

		String resultToken = tokenHelper.createJWT(Integer.toString(userByEmail.getId()), issuerForgotPassword, fPasswordSubject, Long.parseLong(tokenExpiredTime));

		String subject = emailforgotpasswordubject;
		String link = forgotpasswordlink + resultToken;
		String body = link;
		String to = email;

		Email emailObj = new Email();
		emailObj.setTo(to);
		emailObj.setFrom(from);
		emailObj.setSubject(subject);
		emailObj.setBody(body);

		rabbitTemplate.convertAndSend(exchange, routingkey, emailObj);
	}

	@Override
	public void setNewPassword(String token, String password) throws TokenException {

		int id = Integer.parseInt(tokenHelper.parseJWT(token));
		
		User userNewPassword = userRepository.findById(id);

		if (userNewPassword == null) {

			throw new TokenException(environment.getProperty("tokenerrormsg"));
		}

		userNewPassword.setPassword(passwordEncoder.encode(password));
		userRepository.save(userNewPassword);
	}
}