//for registration,enabling user


package com.gamingage.service;


import java.util.Calendar;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamingage.model.ShoppingCart;
import com.gamingage.model.Users;
import com.gamingage.repository.UsersRepository;

@Service
public class UserService {
	
	@Autowired
	private UsersRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	
	public void registerMod(Users user, String password) {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setUser(user);

		user.setPassword(encoder.encode(password));
		user.setEnabled(true);
		user.setRole("MOD");
		user.setCreatedDate(Calendar.getInstance().getTime());
		user.setShoppingCart(shoppingCart);
		userRepo.save(user);	
	}
	
	
	public void registerUser(Users user) {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setUser(user);
		
		user.setPassword(encoder.encode(user.getPassword()));
		user.setEnabled(false);
		user.setRole("USER");
		user.setCreatedDate(Calendar.getInstance().getTime());
		user.setShoppingCart(shoppingCart);
		userRepo.save(user);	
	}

	public void enableUser(Users user) {
		userRepo.save(user);
	}
	

	public Users findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	
	public Users findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public Users findById(Long id) {
		return userRepo.findById(id).get();
	}
	
	
	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}


	
}
