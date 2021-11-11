package com.spring.rest.Controller;

import com.spring.rest.constant.RestURIConstant;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(method=RequestMethod.GET,value = RestURIConstant.HOME)
public class ViewController {
    @ResponseBody
    @GetMapping("/welcome/{name}")
	public String greetHello(@PathVariable String name){
		System.out.println("Hello " + name +" , Welcome.");
		return "Hello, Welcome to View Controller changed, "+name+". Nice to have you here.";
	}
}
