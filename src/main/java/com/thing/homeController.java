package com.thing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class homeController {
	@GetMapping("")
	public String gohome() {
		return "index";
	}
	@GetMapping("/signup")
	public String signUp() {
		return "signInUp/signup";
	}
	@GetMapping("/signin")
	public String signIn() {
		return "signInUp/signin";
	}
	
	
	

	
}
