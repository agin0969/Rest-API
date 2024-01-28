package com.thing.controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class AppController {
	

	@GetMapping("/home")
	public String goHome() {
		return "index";
	}
	@GetMapping("/register")
	public String register() {
		return "signInUp/signup";
	}
	@GetMapping("/signin")
	public String signIn() {
		return "signInUp/signin";
	}


}