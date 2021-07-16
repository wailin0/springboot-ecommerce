package com.gamingage.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamingage.model.BlogPost;
import com.gamingage.model.FAQ;
import com.gamingage.model.Order;
import com.gamingage.model.Users;
import com.gamingage.repository.BlogPostRepository;
import com.gamingage.repository.FAQRepository;
import com.gamingage.repository.OrderRepository;
import com.gamingage.service.UserService;

@RestController
public class RestAPIController {
	
	
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	@Autowired
	private FAQRepository faqRepo;
	
	@GetMapping("/rest/blogPost")
	public List<BlogPost> getBlogPost() {
		return blogPostRepository.findAll();
	}
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@GetMapping("/rest/order")
	public List<Order> getUserOrder(Principal principal) {
		
		Users user = userService.findByEmail(principal.getName());

		return orderRepo.findByUserId(user.getId());
	}
	
	
	@GetMapping("/rest/faq")
	public List<FAQ> getFAQ() {
		return faqRepo.findAll();
	}

}
