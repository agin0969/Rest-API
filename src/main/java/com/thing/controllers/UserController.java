package com.thing.controllers;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.thing.models.User;
import com.thing.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
	public UserController(UserService userService, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager) {
		super();
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}
	
	@GetMapping("/account/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "signInUp/signup";
	}

	@GetMapping("/account/signin")
	public String signin(Model model) {
		model.addAttribute("user", new User());
		return "signInUp/signin";
	}
	@GetMapping("/account/register-success")
	public String register_success() {
		
		return "signInUp/registerSuccessful";
	}
	@PostMapping("/account/signin")
	public ResponseEntity<String> authenticateUser(User user) {
		Authentication authentication= authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>("sign in success", HttpStatus.OK);
	}

	

	@PostMapping("/account/process_register")
	public String signUp( User user, RedirectAttributes redirectAttributes) {
		String error = "tai khoan da co nguoi su dung";
		String error1 = "dang ki thanh cong";
		String username = user.getUsername();
		String password = user.getPassword();
		if (userService.getUserByUsername(user.getUsername()) == null && username != "" && password != "") {
			user.setPassword(passwordEncoder.encode(password));
			userService.saveUser(user);
			redirectAttributes.addFlashAttribute("error1", error1);
			
			return "redirect:/account/register-success";
		} else {
			redirectAttributes.addFlashAttribute("error", error);
			return "redirect:/account/register";
		}
	}
	@PostMapping("/account/logout")
	public String logOut(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response, authentication);
		return "index";
	}
}