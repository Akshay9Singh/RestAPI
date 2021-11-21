package com.spring.rest.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	@RequestMapping(value = RestURIConstant.FILE_DOWNLOAD, method = RequestMethod.GET)
	public ResponseEntity<Object> fileDownload() throws FileNotFoundException {

		String fileName =  "E:\\Demo Data\\File Uploads\\Resume.pdf";
		File myFile = new File(fileName);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(myFile));
		HttpHeaders headers = new HttpHeaders();
		MediaType contentType = MediaType.APPLICATION_PDF;
		try {
			headers.add("Content-Disposition", "attachment;filename = " + myFile.getName());
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in Downloading File");
		}

		ResponseEntity<Object> responseEntity =  ResponseEntity.ok().headers(headers).contentLength(myFile.length()).contentType(contentType).body(resource);
		return responseEntity;
	}
}
