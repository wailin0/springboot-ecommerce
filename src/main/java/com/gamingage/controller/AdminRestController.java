//rest controller for getting product items

package com.gamingage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gamingage.model.Mail;
import com.gamingage.model.Order;
import com.gamingage.model.Product;
import com.gamingage.model.Users;
import com.gamingage.repository.MailRepository;
import com.gamingage.repository.OrderRepository;
import com.gamingage.repository.ProductRepository;
import com.gamingage.repository.UsersRepository;

@RestController
public class AdminRestController {
	
	@Autowired
	private ProductRepository productRepo;
	
	
	@GetMapping("/admin/rest/product/{category}")
	public List<Product> getProduct(@PathVariable("category") String category) {
		return productRepo.findByCategory(category);
	}
	
	@Autowired
	private UsersRepository usersRepo;
	
	@GetMapping("/admin/rest/user/{role}")
	public List<Users> getUser(@PathVariable("role") String role) {
		return usersRepo.findByRole(role);
	}
	
	@Autowired
	private OrderRepository orderRepo;
	
	@GetMapping("/admin/rest/order/{status}")
	public List<Order> getOrder(@PathVariable("status") String status) {
		return orderRepo.findByStatus(status);
	}
	
	@Autowired
	private MailRepository mailRepository;
	
	@GetMapping("/admin/rest/mail")
	public List<Mail> getMail() {
		return mailRepository.findAll();
	}
}
