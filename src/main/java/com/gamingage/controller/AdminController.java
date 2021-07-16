package com.gamingage.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gamingage.model.AdsLink;
import com.gamingage.model.Users;
import com.gamingage.repository.AdsLinkRepository;
import com.gamingage.repository.UsersRepository;

@Controller
public class AdminController {
	
	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private AdsLinkRepository linkRepo;
	
	@GetMapping({"/admin","/admin/dashboard"})
	public String adminDashboard(Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		model.addAttribute("name",name.getUsername());
		
		AdsLink ads = linkRepo.findById(1).orElse(null);
		
		model.addAttribute("ads", ads);
		
		return "admin/dashboard";
	}
	
	@GetMapping("/admin/game")
	public String game(Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		model.addAttribute("name",name.getUsername());
		return "admin/game";
	}
	
	@GetMapping("/admin/giftcard")
	public String giftCard(Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		model.addAttribute("name",name.getUsername());
		return "admin/giftCard";
	}
	
	@GetMapping("/admin/merchandise")
	public String Merchandise(Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		model.addAttribute("name",name.getUsername());
		return "admin/merchandise";
	}
	
	@GetMapping("/admin/customer")
	public String Customer(Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		model.addAttribute("name",name.getUsername());
		return "admin/customer";
	}
	
	@GetMapping("/admin/mod")
	public String Mod(Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		model.addAttribute("name",name.getUsername());
		return "admin/mod";
	}
	
	@GetMapping("/admin/order")
	public String newOrder(Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		model.addAttribute("name",name.getUsername());
		return "admin/newOrder";
	}
	
	@GetMapping("/admin/delivered")
	public String delivered(Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		model.addAttribute("name",name.getUsername());
		return "admin/delivered";
	}

	@GetMapping("/admin/delivering")
	public String delivering(Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		model.addAttribute("name",name.getUsername());
		return "admin/delivering";
	}
	
	@GetMapping("/admin/mail")
	public String mail(Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		model.addAttribute("name",name.getUsername());
		return "admin/mail";
	}
	
	@GetMapping("/admin/blog")
	public String blog(Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		model.addAttribute("name",name.getUsername());
		return "admin/blog";
	}
	
	@GetMapping("/admin/faq")
	public String faq(Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		model.addAttribute("name",name.getUsername());
		return "admin/faq";
	}
}
