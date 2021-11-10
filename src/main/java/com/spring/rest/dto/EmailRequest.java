package com.spring.rest.dto;

public class EmailRequest {

	public String From;
	public String Password;
	public String To;
	public String Subject;
	public String cc;
	public String bcc;
	public String Message;
	
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getFrom() {
		return From;
	}
	public void setFrom(String from) {
		From = from;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
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
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	@Override
	public String toString() {
		return "EmailRequest [From=" + From + ", Message=" + Message + ", Password=" + Password + ", Subject=" + Subject
				+ ", To=" + To + ", bcc=" + bcc + ", cc=" + cc + "]";
	}	
}
