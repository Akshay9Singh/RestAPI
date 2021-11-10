package com.spring.rest.dto;

public class EmailRequest {

	public String From;
	public String Password;
	public String To;
	public String Subject;

	public String getFrom() {
		return this.From;
	}

	public void setFrom(String From) {
		this.From = From;
	}

	public String getPassword() {
		return this.Password;
	}

	public void setPassword(String Password) {
		this.Password = Password;
	}
	public String Message;
	

	public String getTo() {
		return To;
	}
	public void setTo(String to) {
		To = to;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	
	@Override
	public String toString() {
		return "{" +
			" From='" + getFrom() + "'" +
			", Password='" + getPassword() + "'" +
			", To='" + getTo() + "'" +
			", Subject='" + getSubject() + "'" +
			", Message='" + getMessage() + "'" +
			"}";
	}
		
}
