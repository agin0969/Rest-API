package com.thing.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thing.models.User;
import com.thing.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private  UserRepository userRepository;
	
	public <T> T getOrElseThrow(Optional<T> optional, String messString) {
		return optional.orElseThrow(()-> new NoSuchElementException(messString));
	}
	
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	public List<User> getUsers() {
		return userRepository.findAll();
		
	}
	public Optional<User> getUserById(int id) {
		 return userRepository.findById(id);
		
		
	}
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails =(UserDetails) authentication.getPrincipal();
			return new User(userDetails.getUsername(),userDetails.getPassword());
		} else {
			return null;
		}
	}
	
}