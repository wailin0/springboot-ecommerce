package com.gamingage.controller;


import java.io.IOException;
import java.security.Principal;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gamingage.component.AWSS3;
import com.gamingage.component.AdminVerificationMailSender;
import com.gamingage.model.Info;
import com.gamingage.model.AdsLink;
import com.gamingage.model.BlogPost;
import com.gamingage.model.FAQ;
import com.gamingage.model.Order;
import com.gamingage.model.Product;
import com.gamingage.model.Users;
import com.gamingage.repository.InfoRepository;
import com.gamingage.repository.AdsLinkRepository;
import com.gamingage.repository.BlogPostRepository;
import com.gamingage.repository.FAQRepository;
import com.gamingage.repository.OrderRepository;
import com.gamingage.repository.ProductRepository;
import com.gamingage.repository.UsersRepository;
import com.gamingage.service.OrderService;
import com.gamingage.service.UserService;

@Controller
public class AdminCRUDController {
	
	//Game CRUD Section
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private AWSS3 awsS3;

	@PostMapping("/admin/saveProduct")
	public String saveGame(@ModelAttribute("productForm") Product product,RedirectAttributes attributes) throws IOException  {
	
		productRepo.save(product);
		
		String fileName = "product/"+product.getId()+".png";
		awsS3.uploadFile(fileName, product.getImg());
		attributes.addFlashAttribute("success","New Product Added!");
		
		if(product.getCategory().equals("game")) {
			return "redirect:/admin/game"; }
		else if(product.getCategory().equals("giftCard")) {
			return "redirect:/admin/giftcard"; }
		else {
			return "redirect:/admin/merchandise"; }
	}
	
	@PostMapping("/admin/editProduct/{id}")
	public String editProduct(@PathVariable("id") long id, Product product, RedirectAttributes attributes) throws IOException {

		productRepo.save(product);
		
		//if image input isnt empty save it or use existing one
		if(!product.getImg().isEmpty()) {
		String fileName = "product/"+product.getId()+".png";
		awsS3.uploadFile(fileName, product.getImg());
		}
		
		attributes.addFlashAttribute("success","Product updated!");
		
		if(product.getCategory().equals("game")) {
			return "redirect:/admin/game"; }
		else if(product.getCategory().equals("giftCard")) {
			return "redirect:/admin/giftcard"; }
		else {
			return "redirect:/admin/merchandise"; }
		
	}
	
	@GetMapping("/admin/deleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") long id, Model model) {
	    Product product = productRepo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
	    productRepo.delete(product);
	    
	    if(product.getCategory().equals("game")) {
			return "redirect:/admin/game"; }
		else if(product.getCategory().equals("giftCard")) {
			return "redirect:/admin/giftcard"; }
		else {
			return "redirect:/admin/merchandise"; }
	}

///////////////////////////////////////////////////////////////////////////////////////
	
	//User CRUD Section
	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private AdminVerificationMailSender adminMail;
	
	@Autowired
	private UserService usersService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@PostMapping("/admin/saveUser")
	public String saveMod(@ModelAttribute("userForm") Users userForm, Model model) {
		String password = passwordGenerator(8);
		SimpleMailMessage email = adminMail.confirmationMail(userForm, password);
		mailSender.send(email);
		usersService.registerMod(userForm, password);
		model.addAttribute("success","New Account Created! Check email for password!");
			return "redirect:/admin/mod";
	}
	
	@PostMapping("/admin/editUser/{id}")
	public String editUser(@PathVariable("id") long id, Users users, Model model) {
		usersRepo.save(users);
		model.addAttribute("success","Account info updated!");
		
		if(users.getRole().equals("USER")) {
			return "redirect:/admin/customer";	}
		else {
			return "redirect:/admin/mod";	}
	}
	
	@GetMapping("/admin/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
	    Users users = usersRepo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    usersRepo.delete(users);
	    
	    if(users.getRole().equals("USER")) {
			return "redirect:/admin/customer";	}
		else {
			return "redirect:/admin/mod";	}
	}
	
	@GetMapping("/admin/order/{status}/{orderId}")
	public String manageOrder(@PathVariable String status, @PathVariable Long orderId, Model model) {
		Order order = orderService.findById(orderId);
		
		order.setStatus(status);
		
		orderRepo.save(order);
		
		
		if(status.equals("delivering")) {
			return "redirect:/admin/order"; }
		else if(status.equals("delivered")) {
			return "redirect:/admin/delivering"; }
		else {
			return "redirect:/admin/order"; }
	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////
	
	//blog thing
	
	@Autowired
	private BlogPostRepository blogPostRepo;
	
	@PostMapping("/admin/blog")
	public String createBlog(@ModelAttribute("blogForm") BlogPost blogPost,Principal principal, RedirectAttributes attributes) throws IllegalStateException, IOException {
		
		Users user = usersService.findByEmail(principal.getName());
		
		blogPost.setBlogger(user.getUsername());
		blogPostRepo.save(blogPost);
		
		String fileName = "blog/"+blogPost.getId()+".jpg";
		awsS3.uploadFile(fileName, blogPost.getPhoto());

		attributes.addFlashAttribute("createSuccess","A new blog post have been uploaded successfully");
		
		return "redirect:/admin/blog";
	}
	
	@GetMapping("/admin/blogDetail/{id}")
	public String blogDetail(@PathVariable Long id, Principal principal, Model model) {
		Users name= usersRepo.findByEmail(principal.getName());
		
		
		BlogPost blog = blogPostRepo.findById(id).orElse(null);
		
		if(blog == null) {
			return "redirect:/404";
		}
		
		model.addAttribute("blog",blog);
		model.addAttribute("name",name.getUsername());
		return "admin/blogDetail";
	}
	
	@PostMapping("/admin/blogDetail/{id}")
	public String updateBlog(@PathVariable("id") Long id, BlogPost blogPost, RedirectAttributes attributes ) throws IllegalStateException, IOException {
		
		
		BlogPost post = blogPostRepo.findById(id).get();
		
		blogPost.setBlogger(post.getBlogger());
		blogPost.setCreatedDate(post.getCreatedDate());
		blogPostRepo.save(blogPost);
		
		//if image input isnt empty save it or use existing one
		
		if(!blogPost.getPhoto().isEmpty()) {
		String fileName = "blog/"+blogPost.getId()+".jpg";
		awsS3.uploadFile(fileName, blogPost.getPhoto());	
		}
		
		attributes.addFlashAttribute("updateSuccess","Post updated successfully");
		
		return "redirect:/admin/blogDetail/"+id;
	}
	
	
	@GetMapping("/admin/blog/delete/{id}")
	public String deleteBlog(@PathVariable Long id, RedirectAttributes attributes) {
		
		attributes.addFlashAttribute("blogDeleteSuccess", "A blog post deleted!!!");
		blogPostRepo.deleteById(id);
		
		return "redirect:/admin/blog";
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private AdsLinkRepository linkRepository;
	
	//banner photo change
	
	@PostMapping("/admin/changePhoto")
	public String changePhoto(
			AdsLink adsLink,
			@ModelAttribute MultipartFile carousel1,
			@ModelAttribute MultipartFile carousel2,
			@ModelAttribute MultipartFile ads_float,
			@ModelAttribute MultipartFile ads_top,
			@ModelAttribute MultipartFile ads_middle,
			@ModelAttribute MultipartFile ads_bottom,
			@ModelAttribute MultipartFile banner_shop,
			@ModelAttribute MultipartFile ads_sidebar
			) throws IllegalStateException, IOException {

		
		//home page carousel
		if(carousel1 != null && !(carousel1.isEmpty())) {
		String fileName = "page/carousel1.png";
		awsS3.uploadFile(fileName, carousel1);
		}
		
		if(carousel2 != null && !(carousel2.isEmpty())) {
		String fileName = "page/carousel2.png";
		awsS3.uploadFile(fileName, carousel2);
		}
		
	
		//home page ads	
		if(ads_float != null && !(ads_float.isEmpty())) {
		String fileName = "page/ads_float.png";
		awsS3.uploadFile(fileName, ads_float);
		}
		
		if(ads_top != null && !(ads_top.isEmpty())) {
		String fileName = "page/ads_top.png";
		awsS3.uploadFile(fileName, ads_top);
		}
		
		if(ads_middle != null && !(ads_middle.isEmpty())) {
		String fileName = "page/ads_middle.png";
		awsS3.uploadFile(fileName, ads_middle);
		}
		
		if(ads_bottom != null && !(ads_bottom.isEmpty())) {
		String fileName = "page/ads_bottom.png";
		awsS3.uploadFile(fileName, ads_bottom);
		}
		
		
		//shop page
		if(banner_shop != null && !(banner_shop.isEmpty())) {
		String fileName = "page/banner_shop.png";
		awsS3.uploadFile(fileName, banner_shop);
		}
		
		if(ads_sidebar != null && !(ads_sidebar.isEmpty())) {
		String fileName = "page/ads_sidebar.png";
		awsS3.uploadFile(fileName, ads_sidebar);
		}
		
		
		linkRepository.save(adsLink);

		return "redirect:/admin";	
	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// FAQ crud
	@Autowired
	private FAQRepository faqRepo;
	
	@PostMapping("/admin/saveFAQ")
	public String saveFAQ(FAQ faq, RedirectAttributes attributes) {
		
		faqRepo.save(faq);
		
		attributes.addFlashAttribute("success", "a new FAQ created!");
		return "redirect:/admin/faq";
	}
	
	@PostMapping("/admin/editFAQ/{id}")
	public String editFAQ(@PathVariable("id") long id, FAQ faq, RedirectAttributes attributes) {

		faqRepo.save(faq);
		
	
		attributes.addFlashAttribute("success","FAQ updated!");
		
		return "redirect:/admin/faq";
		
	}
	
	@GetMapping("/admin/deleteFAQ/{id}")
	public String deleteFAQ(@PathVariable("id") long id, RedirectAttributes attributes) {
	    faqRepo.deleteById(id);
	    
	    attributes.addFlashAttribute("success", "a FAQ deleted");
		return "redirect:/admin/faq";
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////

	// about us edit
	
	@Autowired
	private InfoRepository infoRepo;
	
	@GetMapping("/admin/info")
	public String aboutUs(Model model, Principal principal) {
		Users name= usersRepo.findByEmail(principal.getName());
		
		Info info = infoRepo.findById(1).orElse(null);
		
		model.addAttribute("info", info);
		model.addAttribute("name",name.getUsername());
		return "admin/info";
	}
	
	
	@PostMapping("/admin/info")
	public String editAboutUs(Info info, RedirectAttributes attributes) {
				
		infoRepo.save(info);
		
		attributes.addFlashAttribute("updateSuccess", "info updated!");
		return "redirect:/admin/info";
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////
	
	
	///password generator for mod acccount
	static String passwordGenerator(int length) 
	{
		String p = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		char[] password = new char[length]; 

        for (int i = 0; i < length; i++) 
        { 
            password[i] =  p.charAt(random.nextInt(p.length())); 
        }
		return new String(password);	
	} 

	
}