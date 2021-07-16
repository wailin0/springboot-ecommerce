package com.gamingage.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.gamingage.model.Info;
import com.gamingage.model.AdsLink;
import com.gamingage.model.BlogPost;
import com.gamingage.model.Mail;
import com.gamingage.model.Product;
import com.gamingage.repository.InfoRepository;
import com.gamingage.repository.AdsLinkRepository;
import com.gamingage.repository.BlogPostRepository;
import com.gamingage.repository.MailRepository;
import com.gamingage.repository.ProductRepository;

@Controller
public class HomeController {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private BlogPostRepository blogPostRepo;
	
	@Autowired
	private InfoRepository infoRepo;
	
	@Autowired
	private AdsLinkRepository linkRepo;
	
	@GetMapping({"/","/index","/home"})
	public String home(Model model) {
		
		List<Product> gproducts = productRepo.getTop5ByCategoryOrderByIdDesc("game");
		List<Product> gCproducts = productRepo.getTop5ByCategoryOrderByIdDesc("giftCard");
		List<Product> mproducts = productRepo.getTop5ByCategoryOrderByIdDesc("merchandise");
		
		List<BlogPost> blogPost = blogPostRepo.findAllByOrderByIdDesc();
	
		model.addAttribute("gproducts",gproducts);
		model.addAttribute("gCproducts",gCproducts);
		model.addAttribute("mproducts",mproducts);
		
		model.addAttribute("blogPost",blogPost);
		
		Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
    	
    	AdsLink ads =  linkRepo.findById(1).orElse(null);
    	model.addAttribute("ads", ads);
		return "home";
	}
	
	
    @GetMapping("/shop")
    public String store(Model model){
    	
    	Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
    	
    	AdsLink ads =  linkRepo.findById(1).orElse(null);	
    	model.addAttribute("ads", ads);
    	
        return "shop";
    }
    
    
    @GetMapping("/aboutus")
    public String aboutus(Model model){
    	
    	
    	Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
    	
        return "about";
    }
    
    @GetMapping("/faq")
    public String faq(Model model){
    	
    	Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
    	
        return "faq";
    }
    
    
    @GetMapping("/blog")
    public String blog(Model model){
    	
    	Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
    	
        return "blog";
    }
 
    
    @GetMapping("/contact")
    public String about(Model model){
    	
    	Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
    	
        return "contact";
    }
    
 
    
    @GetMapping("/recover")
    public String recoverAccount(Model model){
    	
    	Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
    	
        return "recoverAccount";
    }
    
    @Autowired
    private MailRepository mailRepo;
    
    @PostMapping("/contact")
    public String sendMail(@ModelAttribute Mail mailForm, Model model) {
    	
    	Mail mail = new Mail();
    	mail.setSender(mailForm.getSender());
    	mail.setPhone(mailForm.getPhone());
    	mail.setEmail(mailForm.getEmail());
    	mail.setMessage(mailForm.getMessage());
    	mail.setSentDate(Calendar.getInstance().getTime());
    	mailRepo.save(mail);
    	
    	model.addAttribute("sendSuccess","Thank u for contacting us we will get back to u ASAP");
    	
    	return "contact";
    }
    
    
    @Autowired
    private BlogPostRepository postRepo;
    
    @GetMapping("/blog/{title}")
    public String blogDetail(@PathVariable String title, Model model) {
    	
    	BlogPost post = postRepo.findByTitleContainingIgnoreCase(title);
    	if(post == null) {
    		return "redirect:/404";
    	}
    	
    	Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
    	
    	model.addAttribute("post", post);
    	
    	return "/blogDetail";
    }
    
    

}
