package com.thing.controllers;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/admin")
	public String getUser(Model model){
		List<User> users = userService.getUsers();
		model.addAttribute("users",users);
		return "admin/api";
		
	}
	@PostMapping("/signup")
	public String signUp(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
	
		String username=user.getUsername();
		String password=user.getPassword();
		if(userService.getUserByUsername(username)==null && username !="" && password!="") {
			userService.saveUser(user);
	
		}
		else {
			redirectAttributes.addFlashAttribute("error", "hay nhap day du thong tin");
			
		}
		return "redirect:/";
	}

	
	
	
}
