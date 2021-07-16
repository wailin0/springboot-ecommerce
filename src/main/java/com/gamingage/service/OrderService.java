package com.gamingage.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamingage.model.CartItem;
import com.gamingage.model.Order;
import com.gamingage.model.Product;
import com.gamingage.model.ShoppingCart;
import com.gamingage.model.Users;
import com.gamingage.repository.OrderRepository;

@Service
public class OrderService{

	@Autowired 
	private OrderRepository orderRepository;
	
	@Autowired
	private CartItemService cartItemService;
	
	public Order createOrder(ShoppingCart shoppingCart,Users user) {
		Order order = new Order();
		order.setStatus("pending");
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		
		//update stock number of each item
		for(CartItem cartItem : cartItemList) {
			Product product = cartItem.getProduct();
			cartItem.setOrder(order);
			product.setStock(product.getStock() - cartItem.getQty());
		}
		
		order.setCartItemList(cartItemList);
		order.setDate(Calendar.getInstance().getTime());
		order.setTotal(shoppingCart.getGrandTotal());
		order.setUser(user);
		order = orderRepository.save(order);
		
		return order;
	}
	
	public Order findById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}
}
