package com.thing.controllers;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/admin")
	public String getUser(Model model) {
		List<User> users = userService.getUsers();
		model.addAttribute("users", users);
		return "admin/api";

	}

	@PostMapping("/signup")
	public String signUp(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		String error = "tai khoan da co nguoi su dung";
		String username = user.getUsername();
		String password = user.getPassword();
		if (userService.getUserByUsername(username) == null && username != "" && password != "") {
			user.setPassword(passwordEncoder.encode(password));
			userService.saveUser(user);
			return "redirect:/";

//			PasswordEncoder passwordEnocder = new BCryptPasswordEncoder();
//			if (passwordEncoder.matches(rawPassword, encodedPassword)) {
//			    System.out.println("Matched!");
//			}
		} else {
			redirectAttributes.addFlashAttribute("error", error);
			return "redirect:/signup";
		}

	}

	@GetMapping("/admin/error")
	public String checkSession(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication.isAuthenticated()) {
			String userNameString = authentication.getName();
			model.addAttribute("userName", userNameString);
			return "admin/error";
		}
		else {
			
			return "signInUp/signin";
		}
		

		
	}

}
