package com.example.oldstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	@GetMapping("/")
	public String intro(){
		return "intro"; 
	}	
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
}
