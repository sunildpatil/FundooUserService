package com.bridgelabz.user.services;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.user.models.Email;

import ch.qos.logback.classic.Logger;

@Component
public class MessageService {

	@Autowired
	private JavaMailSender javaMailSender;

	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@RabbitListener(queues="${queue}")
	public void sendMail(Email email) {

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setFrom(email.getFrom());
		mail.setTo(email.getTo());
		mail.setSubject(email.getSubject());
		mail.setText(email.getBody());

		logger.info("Sending...");
		javaMailSender.send(mail);
		logger.info("Done!");
	}
}		