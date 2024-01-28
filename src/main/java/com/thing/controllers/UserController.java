package com.thing.controllers;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.thing.models.User;
import com.thing.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;

	@Autowired
	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	

	@PostMapping("/process_register")
	public String signUp(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		String error = "tai khoan da co nguoi su dung";
		String username = user.getUsername();
		String password = user.getPassword();
		if (userService.getUserByUsername(username) == null && username != "" && password != "") {
			user.setPassword(passwordEncoder.encode(password));
			userService.saveUser(user);
			return "redirect:/user/index";

//			PasswordEncoder passwordEnocder = new BCryptPasswordEncoder();
//			if (passwordEncoder.matches(rawPassword, encodedPassword)) {
//			    System.out.println("Matched!");
//			}
		} else {
			redirectAttributes.addFlashAttribute("error", error);
			return "redirect:/register";
		}

	}
	@GetMapping("/admin/api")
	public String adminApi(Model model) {
		List<User> list= userService.getUsers();
		model.addAllAttributes(list);
		return "admin/api";
	}
	@GetMapping("/admin/error")
	public String adminApi() {
	
		return "admin/error";
	}
	
	


}
