package com.thing.controllers;

import java.util.List;

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

import com.thing.models.Product;
import com.thing.models.User;
import com.thing.services.ProductService;
import com.thing.services.UserService;

@Controller

public class AppController {
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	

	@GetMapping("/home")
	public String goHome(Model model, Model model2) {
		List<Product> products = productService.getAllProduct();
		model.addAttribute("products",products);
		User users = userService.getCurrentUser();
		model2.addAttribute("users",users);
		return "index";
	}
	@GetMapping("/admin/api")
	public String getMethodName() {
		return "admin/api";
	}
	

}
