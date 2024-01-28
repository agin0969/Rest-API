package com.thing.services;

import java.util.List;
import java.util.Optional;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thing.models.User;
import com.thing.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private  UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	public List<User> getUsers() {
		return userRepository.findAll();
		
	}
	public Optional<User> getUserById(int id) {
		Optional<User> usOptional =userRepository.findById(id);
		return usOptional ;
		
	}
	
	public User getUserByUsername(String username){
		User finduser=userRepository.findByUsername(username);
		return finduser;
	}
	public User saveUser(User user) {
		return userRepository.save(user);
	}
}
