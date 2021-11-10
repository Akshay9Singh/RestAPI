package com.spring.rest.Controller;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.rest.constant.RestURIConstant;
import com.spring.rest.dto.EmailRequest;

//Commit by arnab
@RestController//To specify this class as a Rest type controller
@RequestMapping(RestURIConstant.REST_API)
public class GenericController {	
	
	@Value("${spring.mail.host}")
	String HOST;
	@Value("${spring.mail.port}")
	int PORT;
	@PostMapping(RestURIConstant.SEND_EMAIL)
	public ResponseEntity<String> sendEmailAPI(@RequestBody EmailRequest erq) {

		SimpleMailMessage message = new SimpleMailMessage();
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		try {
			message.setFrom(erq.getFrom());
			message.setTo(erq.getTo());
			message.setSubject(erq.getSubject());
			message.setText(erq.getMessage());
			message.setCc(erq.getCc());
			message.setBcc(erq.getBcc());

			System.out.println(message);
			
			Properties properties = mailSender.getJavaMailProperties();
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.ssl.trust", "*");
			properties.put("mail.transport.protocol", "smtp");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.debug", "true");
			mailSender.setHost(HOST);
			mailSender.setPort(PORT);
			mailSender.setUsername(erq.getFrom());
			mailSender.setPassword(erq.getPassword());
			mailSender.setJavaMailProperties(properties);
			
			mailSender.send(message);
			
			System.out.println(mailSender.toString());
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in Sending Email");
		}

		return new ResponseEntity<String>("Mail Sent Successfully",HttpStatus.OK);
	}

	@GetMapping("/home/{name}")
	public String greetHello(@PathVariable String name){
		System.out.println("Hello " + name +" , Welcome.");
		return "Hello, Welcome "+name+". Nice to have you here.";
	}
}
