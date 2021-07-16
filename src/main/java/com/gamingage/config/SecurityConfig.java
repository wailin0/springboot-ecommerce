//spring security config 

package com.gamingage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
		
	}
	

	//decrypt the password from login and authenticate users
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());

	}


	//configuration for login and logout
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/","/home","/index","/register","/faq","/shop","contact","product","cartpage").permitAll()  //pages accessible by everyone
		.antMatchers("/user/**","/cart","/addItem","/order","/account/**").hasAnyRole("USER","ADMIN","MOD")
		.antMatchers("/admin/**").hasAnyRole("ADMIN", "MOD")
		.and()         //method chaining    
		.formLogin().loginPage("/login").usernameParameter("email").permitAll()    
		.and()
		.logout()
		.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("gameingage-remember-me")  //when user logout delete every cookie and session
		.logoutUrl("/logout").permitAll()
		.and().headers().frameOptions().sameOrigin()   //to prevent clickjacking attack
		.and()
		.rememberMe().key("myUniqueKey")
		.rememberMeCookieName("gameingage-remember-me")
		.tokenValiditySeconds(604800);   //time before cookie expire
	}
	
	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception{
		return authenticationManager();
	}


	
	
}