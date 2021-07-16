package com.gamingage.controller;



import java.security.Principal;
import java.util.List;
import java.util.Locale;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gamingage.component.UserVerificationMailSender;
import com.gamingage.model.CartItem;
import com.gamingage.model.Info;
import com.gamingage.model.Order;
import com.gamingage.model.Product;
import com.gamingage.model.ShoppingCart;
import com.gamingage.model.Users;
import com.gamingage.repository.CartItemRepository;
import com.gamingage.repository.InfoRepository;
import com.gamingage.repository.ProductRepository;
import com.gamingage.service.CartItemService;
import com.gamingage.service.OrderService;
import com.gamingage.service.ShoppingCartService;
import com.gamingage.service.UserService;

@Controller
public class ShoppingCartController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private CartItemRepository cartItemRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserVerificationMailSender userVerificationMailSender;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private InfoRepository infoRepo;
	
	@PostMapping("/checkout")
	public String Checkout(Model model, Principal principal) {
		
		
		Users user = userService.findByEmail(principal.getName());
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());
	
		ShoppingCart shoppingCart = userService.findByEmail(principal.getName()).getShoppingCart();
		
		if(cartItemList.size() == 0) {
			return "redirect:/cart";
		}
		
		Order order = orderService.createOrder(shoppingCart, user);
		
		mailSender.send(userVerificationMailSender.OrderConfirmationEmail(user, order, Locale.ENGLISH));
		
		
		shoppingCartService.clearShoppingCart(shoppingCart);
		
		return "redirect:/order";	
	}

	@GetMapping("/product/{productName}")
	public String productDetail(@PathVariable String productName, Model model, Principal principal
			) {
		if (principal != null) {
			Users user = userService.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}
		
		Product product = productRepo.findByNameContainingIgnoreCase(productName);
		
		if(product == null) {
			return "redirect:/404";
		}
		
		model.addAttribute("product", product);
		
		Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
	
		return "product";
	
	}
	
	@GetMapping("/cart")
	public String shoppingCart(Model model, Principal principal) {
		Users user = userService.findByEmail(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		shoppingCartService.updateShoppingCart(shoppingCart);
		
		if(cartItemList.size() == 0) {
			model.addAttribute("emptyCart", true);
		}
		
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", shoppingCart);
		
		Info info = infoRepo.findById(1).orElse(null);
    	model.addAttribute("info", info);
		return "cart";
	}
	
	
	@PostMapping("/addItem")
	public String addItem(
			@ModelAttribute("productForm") Product product,
			@ModelAttribute("qty") String qty,
			RedirectAttributes attributes, Principal principal
			) {
	
		Users user = userService.findByEmail(principal.getName());
		product = productRepo.findByNameContainingIgnoreCase(product.getName());
		
		if (!cartItemService.isEnoughStock(product, user, Integer.parseInt(qty))) {
			attributes.addFlashAttribute("notEnoughStock", true);
			return "redirect:/product/"+product.getName();
		}

		cartItemService.addProductToCartItem(product, user, Integer.parseInt(qty));
		attributes.addFlashAttribute("addSuccess", true);
		
		return "redirect:/product/"+product.getName();
		
	}
	
	@RequestMapping("/updateCartItem")
	public String updateShoppingCart(
			@ModelAttribute("id") Long cartItemId,
			@ModelAttribute("qty") int qty
			) {
		CartItem cartItem = cartItemService.findById(cartItemId);
		cartItem.setQty(qty);
		cartItemService.updateCartItem(cartItem);
		
		return "redirect:/cart";
	}
	
	
	@RequestMapping("/removeItem")
	public String removeItem(
			@RequestParam("id") Long id,Principal principal
			) {
			Users user = userService.findByEmail(principal.getName());
			ShoppingCart shoppingCart = user.getShoppingCart();
		
			cartItemRepo.deleteByIdAndshoppingCartId(id, shoppingCart.getId());
			
			return "redirect:/cart";
	}
}
