package com.thing.services;

import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thing.models.User;
import com.thing.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	public List<User> getUsers() {
		return userRepository.findAll();
		
	}
	public User getUserById(int id) throws NameNotFoundException{
		User finduser=userRepository.findById(id).orElseThrow(()->new NameNotFoundException("id not found"));
		return finduser;
	}
	public User getUserByUsername(String username){
		User finduser=userRepository.findByUsername(username);
		return finduser;
	}
	public User saveUser(User user) {
		return userRepository.save(user);
	}
}
