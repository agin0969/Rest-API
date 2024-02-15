package com.thing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.FormLoginBeanDefinitionParser;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.thing.models.CustomUserDetails;
import com.thing.services.CustomAccessDeniedHandler;
import com.thing.services.CustomAuthenticateFailureHandler;
import com.thing.services.CustomerUserDetailService;

import jakarta.websocket.Session;

@Configuration
@EnableWebSecurity
public class SercurityConfig {
	HeaderWriterLogoutHandler clearHandler = new HeaderWriterLogoutHandler(
			new ClearSiteDataHeaderWriter(Directive.COOKIES));

	@Bean
	TokenBasedRememberMeServices rememberMeServices() {
		RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
		TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("thing", userDetailsService(),
				encodingAlgorithm);
		rememberMe.setTokenValiditySeconds(60000);
		rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
		return rememberMe;
	}

	@Bean
	AccessDeniedHandler cusAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();

	}

	@Bean
	UserDetailsService userDetailsService() {
		return new CustomerUserDetailService();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/account/**").permitAll()
				.requestMatchers("/resources/**").permitAll().requestMatchers("/home").permitAll()
				.requestMatchers("/admin/api").hasAuthority("ADMIN").anyRequest().permitAll())
				.exceptionHandling(customizer -> customizer.accessDeniedHandler(cusAccessDeniedHandler()))
				.formLogin(formLogin -> formLogin.loginPage("/account/signin")

						.usernameParameter("username").defaultSuccessUrl("/home").permitAll()
						.failureHandler(new CustomAuthenticateFailureHandler())

				).rememberMe((remember) -> remember.rememberMeServices(rememberMeServices()).key("thing"))
				.logout((logout) -> logout.logoutSuccessUrl("/home").permitAll().addLogoutHandler(clearHandler))
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

		return http.build();
	}

}