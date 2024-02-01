package com.thing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thing.models.User;
import com.thing.services.UserService;

@Controller

public class AppController {
	@Autowired
	private UserService userService;
	
	

	@GetMapping("/home")
	public String goHome(Model model) {
		User users = userService.getCurrentUser();
		model.addAttribute("users",users);
		return "index";
	}
	@GetMapping("/admin/api")
	public String getMethodName() {
		return "admin/api";
	}
	

}
