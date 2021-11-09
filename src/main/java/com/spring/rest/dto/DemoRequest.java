package com.spring.rest.dto;

public class DemoRequest {
	
	public String Request;

	public String getRequest() {
		return Request;
	}

	public void setRequest(String request) {
		Request = request;
	}

	@Override
	public String toString() {
		return "DemoRequest [Request=" + Request + "]";
	}

}
