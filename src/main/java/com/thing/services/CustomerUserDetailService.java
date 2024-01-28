package com.thing.services;

import org.hibernate.bytecode.internal.bytebuddy.PrivateAccessorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thing.models.CustomUserDetails;
import com.thing.models.CustomUserdetails;
import com.thing.models.User;
import com.thing.repositories.UserRepository;

@Service
public class CustomerUserDetailService implements UserDetailsService {
	@Autowired
	private UserService userService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= userService.getUserByUsername(username);
		if (user == null ) {
			throw new UsernameNotFoundException("not found");
		}
		
		return new CustomUserDetails(user);
	}


}
