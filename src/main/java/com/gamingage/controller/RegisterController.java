//controller for registration,email confirmation

package com.gamingage.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gamingage.service.UserService;
import com.gamingage.component.UserValidator;
import com.gamingage.component.UserVerificationMailSender;
import com.gamingage.model.Users;
import com.gamingage.repository.InfoRepository;
import com.gamingage.model.Info;
import com.gamingage.model.Token;
import com.gamingage.service.TokenService;

@Controller
public class RegisterController {
	
	@Autowired
	private UserVerificationMailSender userVerificationMailSender;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private InfoRepository infoRepo;

	//for getting register page
	@GetMapping("/register")
	public String regster(Model model) {
		model.addAttribute("userForm", new Users());
		
		Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
		
		return "register";
	}
	
	
	//saving request for userinfo
	@PostMapping("/register")
	public String register(@ModelAttribute("userForm") Users userForm,BindingResult bindingResult,Model model, HttpServletRequest request, RedirectAttributes att) {
		userValidator.validate(userForm, bindingResult);
		
		//if theres error while registering reload page
		if(bindingResult.hasErrors()) {
			Info info = infoRepo.findById(1).orElse(null);
	    	model.addAttribute("info", info);
			return "register";
		}

		userService.registerUser(userForm);      //register userinfo into databasse
		String token = UUID.randomUUID().toString();     //generate random token number
		tokenService.createMailConfirmationToken(userForm, token);  //save token into database
		
		//get url and token 
		String appUrl = "www.gamyanmar.com/confirmEmail?token=" + token;
		
		SimpleMailMessage email = userVerificationMailSender.confirmationMail(appUrl, userForm);  //get email contents from VerificationMailSender class
		mailSender.send(email);            //send verification mail to user
		
		att.addFlashAttribute("registerSuccess", "Register sucessful,please check your email for activiation link");
		return "redirect:/login";    //if register success redirect to login page
	}
	
	
	@GetMapping("/invalidtoken")
	public String BadRequest(Model model) {
		
		Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
		return "invalidToken";
	}
	
	
	//for verifying email
	@GetMapping("/confirmEmail")
	public String comfirmEmail(@RequestParam("token") String token,RedirectAttributes att) {
		Token verificationToken = tokenService.getToken(token);
		
		//check if token is valid or not
		if(verificationToken == null) {
			att.addFlashAttribute("invalidToken", "invalid token");
			return "redirect:/invalidtoken";
		}
		
		//check if token was expired or not
		Users user = verificationToken.getUser();
		
		
		//enable user if above 2 conditions are false
		user.setEnabled(true);
		userService.enableUser(user);
		tokenService.deleteToken(verificationToken);
		
		//redirect with success messages
		att.addFlashAttribute("activationSuccess","Your account has been successfully activated!");
		return "redirect:/login";
	}
	
	
	
}

