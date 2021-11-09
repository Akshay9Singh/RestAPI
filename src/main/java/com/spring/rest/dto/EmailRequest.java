package com.spring.rest.dto;

public class EmailRequest {

	public String To;
	public String Subject;
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
		return "EmailRequest [To=" + To + ", Subject=" + Subject + ", Message=" + Message + "]";
	}	
}
