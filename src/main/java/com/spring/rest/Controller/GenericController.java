package com.spring.rest.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import javax.mail.Transport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.rest.constant.RestURIConstant;
import com.spring.rest.dto.EmailRequest;


@RestController//To specify this class as a Rest type controller
@RequestMapping(RestURIConstant.REST_API)
public class GenericController {	
	
	@Value("${spring.mail.username}")
	String FROM;
	@Value("${spring.mail.password}")
	String PASSWORD;
	@Value("${spring.mail.host}")
	String HOST;
	@Value("${spring.mail.port}")
	int PORT;
	@RequestMapping(value = RestURIConstant.SEND_EMAIL, method = RequestMethod.POST)
	public ResponseEntity sendEmailAPI(@RequestBody EmailRequest erq) {

		SimpleMailMessage message = new SimpleMailMessage();
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		try {
			message.setFrom(FROM);
			message.setTo(erq.getTo());
			message.setSubject(erq.getSubject());
			message.setText(erq.getMessage());

			System.out.println(message);
			
			Properties properties = mailSender.getJavaMailProperties();
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.ssl.trust", "*");
			properties.put("mail.transport.protocol", "smtp");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.debug", "true");
			mailSender.setHost(HOST);
			mailSender.setPort(PORT);
			mailSender.setUsername(FROM);
			mailSender.setPassword(PASSWORD);
			mailSender.setJavaMailProperties(properties);
			
			mailSender.send(message);
			
			System.out.println(mailSender.toString());
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in Sending Email");
		}

		return new ResponseEntity("Mail Sent....",HttpStatus.OK);
	}
}
