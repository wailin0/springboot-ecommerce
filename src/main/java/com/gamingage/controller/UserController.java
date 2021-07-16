package com.gamingage.controller;

import java.security.Principal;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.gamingage.component.UserVerificationMailSender;
import com.gamingage.model.Info;
import com.gamingage.model.Token;
import com.gamingage.model.Users;
import com.gamingage.repository.InfoRepository;
import com.gamingage.repository.TokenRepository;
import com.gamingage.repository.UsersRepository;
import com.gamingage.service.TokenService;
import com.gamingage.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private InfoRepository infoRepo;
	
	@GetMapping("/login")
	public String login(Model model,String error) {
		if(error !=null)
			model.addAttribute("error", "Your email or password is incorrect");
		
		Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
    	
		return "login";
	}
	
	@GetMapping({"/account"})
	public String account(Principal principal, Model model) {
		
		Users user = userService.findByEmail(principal.getName());
		
		model.addAttribute("user", user);
		
		Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
		
		return "account";
	}
	  
	
	@GetMapping("/order")
	public String userOrder(Model model) {
		
		Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
		
		return "order";
	}
	
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UsersRepository userRepo;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserVerificationMailSender userVerificationMailSender;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private TokenRepository tokenRepo;
	
	
	@PostMapping("/recover")
	public String forgetPassword(
			@ModelAttribute("email") String email,
			Model model
			) {
	Users user = userService.findByEmail(email);
	
	if (user == null ) {
		model.addAttribute("emailNotExist", true);
		Info info = infoRepo.findById(1).orElse(null);
		model.addAttribute("info", info);
		return "recoverAccount";
	}
	
	String token = UUID.randomUUID().toString();
	tokenService.createPasswordResetToken(user, token);
	
	//get url and token 
	String appUrl = "www.gamyanmar.com/passwordResetToken/" + token;
	
	SimpleMailMessage newEmail = userVerificationMailSender.passwordResetTokenEmail(appUrl, email, user);
	
	mailSender.send(newEmail);
	
	model.addAttribute("emailSentSuccess", "true");
	Info info = infoRepo.findById(1).orElse(null);
	model.addAttribute("info", info);
	
	return "recoverAccount";
	}
	
	
	//for processing password reset email token
	@GetMapping("/passwordResetToken/{token}")
	public String passwordResetToken(@PathVariable String token, RedirectAttributes att, Model model) {
		Token verificationToken = tokenService.getToken(token);

		if(verificationToken == null) {
			att.addFlashAttribute("invalidToken", "invalid token");
			
			return "redirect:/invalidtoken";
		}
		
		Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
		model.addAttribute("token", token);
		return "resetPassword";
	}
		
	@PostMapping("/resetpassword")
	public String resetPassword(@ModelAttribute("token") String token,
			@ModelAttribute("password") String password,
			@ModelAttribute("confirmPassword") String confirmPassword,
			Model model, RedirectAttributes attributes) {
		
		
		
		if(!password.equals(confirmPassword)) {
			Info info = infoRepo.findById(1).orElse(null);
	    	model.addAttribute("info", info);
			model.addAttribute("pwNotEqual",true);
			
			
			return "resetPassword";
		}
		
		Token verificationToken = tokenService.getToken(token);
		Users user = userService.findById(verificationToken.getUser().getId());
		user.setPassword(encoder.encode(password));
		userRepo.save(user);
		
		tokenRepo.deleteById(verificationToken.getId());
		
		return "redirect:/logout";
	}
	
	
	@GetMapping("/account/update/{info}")
	public String getupdateAccountPage(@PathVariable String info, Model model) {
		
		if(info.equals("username")) {
			model.addAttribute("updateInfo", "Update Your Name");
			model.addAttribute("change", info);
		}
		
		if(info.equals("email")) {
			model.addAttribute("updateInfo", "Update Your Email");
			model.addAttribute("change", info);
		}
		
		if(info.equals("phone")) {
			model.addAttribute("updateInfo", "Update Your Phone");
			model.addAttribute("change", info);
		}
		
		if(info.equals("address")) {
			model.addAttribute("updateInfo", "Update Your Address");
			model.addAttribute("change", info);
		}
		
		Info info1 = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info1);
		return "updateAccount";
	}
	
	@PostMapping("/account/update/{info}")
	public String updateAccount(Principal principal, @PathVariable String info, @ModelAttribute("update") Users updatedInfo, RedirectAttributes attributes, HttpServletRequest request) {
		
		Users user = userService.findByEmail(principal.getName());
		
		if(info.equals("username")) {
			user.setUsername(updatedInfo.getUsername());
			userRepo.save(user);
		}
		
		if(info.equals("email")) {
			
			if(userRepo.existsByEmail(updatedInfo.getEmail())){
				attributes.addFlashAttribute("emailExists", "account with that email already exists");
				return "redirect:/account/update/email";
			}
			
			
			
			String token = UUID.randomUUID().toString();
			tokenService.createMailConfirmationToken(user, token);

			//get url and token 
			String appUrl = "www.gamyanmar.com/account/update/email/" + updatedInfo.getEmail()+"/"+token;
			
			SimpleMailMessage newEmail = userVerificationMailSender.emailUpdateTokenEmail(appUrl, updatedInfo.getEmail(), user);
			
			mailSender.send(newEmail);
			
			attributes.addFlashAttribute("emailSentSuccess", true);
			
			return "redirect:/account/update/email";
		}
		
		if(info.equals("phone")) {
			if(!updatedInfo.getPhone().matches("([0-9 ]+)")){
				attributes.addFlashAttribute("wrongPhone", "invalid phone number!");
				return "redirect:/account/update/phone";
			}
			user.setPhone(updatedInfo.getPhone());
			userRepo.save(user);
		}
		
		if(info.equals("address")) {
			user.setAddress(updatedInfo.getAddress());
			userRepo.save(user);
		}
		
		attributes.addFlashAttribute("updateSccess", info+" updated successfully");
		return "redirect:/account";
	}
	
	
	@GetMapping("/account/update/email/{email}/{token}")
	public String updateNewEmail(Principal principal, @PathVariable String email, @PathVariable String token, RedirectAttributes attributes) {
		
		Token verificationToken = tokenService.getToken(token);

		if(verificationToken == null) {
			attributes.addFlashAttribute("invalidToken", "invalid token");
			
			return "redirect:/invalidtoken";
		}
		
		
		Users user = userService.findByEmail(principal.getName());
		user.setEmail(email);
		userRepo.save(user);
		
		tokenRepo.deleteById(verificationToken.getId());

		return "redirect:/logout";
	}
	
	
		
		
}
