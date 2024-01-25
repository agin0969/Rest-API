package com.thing;


import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.FormLoginBeanDefinitionParser;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SercurityConfig   {
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http 
        	.authorizeHttpRequests(auth -> auth
        			.requestMatchers("/**").permitAll()
        			.anyRequest().authenticated()
        			)
        			.formLogin(formLogin -> formLogin
        					.loginPage("/")
        					.permitAll()
        			)
        			.rememberMe(Customizer.withDefaults());
        return http.build();
    }
}