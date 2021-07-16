//for login

package com.gamingage.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.gamingage.model.Users;
import com.gamingage.repository.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersRepository userRepo;
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users user = userRepo.findByEmail(email);
		
		if(user == null ) throw new UsernameNotFoundException("email not found");
		
		Set<GrantedAuthority> authorities = new HashSet<>();
		if(user.getRole().equals("ADMIN")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		else if(user.getRole().equals("MOD")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_MOD"));
		}
		
		else {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));}
		
		return new User(user.getEmail(),user.getPassword(),user.isEnabled(),true, true, true, authorities);
	}

}
