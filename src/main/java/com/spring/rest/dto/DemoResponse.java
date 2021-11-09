package com.spring.rest.dto;

public class DemoResponse {

	public String Response;

	public String getResponse() {
		return Response;
	}

	public void setResponse(String response) {
		Response = response;
	}

	@Override
	public String toString() {
		return "DemoResponse [Response=" + Response + "]";
	}
}
