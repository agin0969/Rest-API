package com.thing;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.FormLoginBeanDefinitionParser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.thing.models.CustomUserDetails;
import com.thing.services.CustomUserDetailService;
import com.thing.services.CustomerUserDetailService;

@Configuration
@EnableWebSecurity
public class SercurityConfig   {
	@Bean
	UserDetailsService userDetailsService() {
		return new CustomerUserDetailService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	 @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailsService());
	        authProvider.setPasswordEncoder(passwordEncoder());
	         
	        return authProvider;
	    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider());
        http 
        	.authorizeHttpRequests(auth -> auth
        			.requestMatchers("/","/register").permitAll()
        			
        			.requestMatchers("/admin/api").hasAuthority("USER")
        			.anyRequest().authenticated()
        			)
        			.formLogin(formLogin -> formLogin
        					.loginPage("/signin")
        					
        				.usernameParameter("username")
        				.defaultSuccessUrl("/admin/api")
        				.permitAll()
        					
        				 	
        			);
        			
        return http.build();
    }
	
	
	
	
}