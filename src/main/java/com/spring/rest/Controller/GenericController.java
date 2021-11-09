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
import com.spring.rest.dto.DemoRequest;
import com.spring.rest.dto.DemoResponse;
import com.spring.rest.dto.EmailRequest;

//Controller
@RestController//To specify this class as a Rest type controller
@RequestMapping(RestURIConstant.REST_API)
public class GenericController {

	@RequestMapping(value = RestURIConstant.DEMO, method = RequestMethod.POST)
	public ResponseEntity demoAPI(@RequestBody DemoRequest input) {
		System.out.println("Request is : " + input);

		DemoResponse rsp = new DemoResponse();
		rsp.setResponse("API worked");

		return ResponseEntity.ok(rsp);
	}


	@Value("${file.upload.location}")//To read file location directory from aplication.properties
	String FILE_DIRECTORY;//Contains file location directory
	@RequestMapping(value = RestURIConstant.FILE_UPLOAD, method = RequestMethod.POST)
	public ResponseEntity fileUpload(@RequestParam("File") MultipartFile file) {

		System.out.println(file);
		System.out.println(file.getContentType());
		System.out.println(file.getName());
		System.out.println(file.getSize());
		System.out.println(file.getResource());
		System.out.println(file.getOriginalFilename());
		
        /*try {
			file.transferTo(new File(FILE_DIRECTORY+file.getOriginalFilename()));
		} catch (Exception e) {
			e.printStackTrace();
		}//This type of implementation simply copies the file from one directory to another*/
		
		try {
			File myFile = new File(FILE_DIRECTORY+file.getOriginalFilename());
			myFile.createNewFile();

			FileOutputStream fos = new FileOutputStream(myFile);
			fos.write(file.getBytes());
			fos.close();

		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in Uploading File");
		}

		return new ResponseEntity("File Uploaded Successfully",HttpStatus.OK);
	}
	
	/*{ttpHeaders header = new HttpHeaders();
    header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
    header.add("Cache-Control", "no-cache, no-store, must-revalidate");
    header.add("Pragma", "no-cache");
    header.add("Expires", "0");

    Path path = Paths.get(file.getAbsolutePath());
    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

    return ResponseEntity.ok()
            .headers(header)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);}*/
	
	
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
