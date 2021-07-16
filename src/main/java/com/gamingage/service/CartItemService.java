package com.gamingage.service;



import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamingage.model.CartItem;
import com.gamingage.model.Product;
import com.gamingage.model.ShoppingCart;
import com.gamingage.model.Users;
import com.gamingage.repository.CartItemRepository;



@Service
public class CartItemService{
	
	@Autowired
	private CartItemRepository cartItemRepository;

	
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}
	
	
	public CartItem addProductToCartItem(Product product, Users user, int qty) {
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
		
		for (CartItem cartItem: cartItemList) {
			if (product.getId() == cartItem.getProduct().getId()) {
				cartItem.setQty(cartItem.getQty() + qty);
				cartItem.setSubtotal(new BigDecimal(product.getPrice()).multiply(new BigDecimal(cartItem.getQty())));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}
		
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setProduct(product);
		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(product.getPrice()).multiply(new BigDecimal(cartItem.getQty())));
		
		cartItem = cartItemRepository.save(cartItem);
		
		
		return cartItem;
		
	}
	
	public CartItem updateCartItem(CartItem cartItem) {
		BigDecimal bigDecimal = new BigDecimal(cartItem.getProduct().getPrice()).multiply(new BigDecimal(cartItem.getQty()));
		
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bigDecimal);
		
		cartItemRepository.save(cartItem);
		
		return cartItem;
	}


	public 	boolean isEnoughStock(Product product, Users user, int qty) {
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
		
		for (CartItem cartItem: cartItemList) {
			if (product.getId() == cartItem.getProduct().getId()) {
				if (cartItem.getQty()+qty>product.getStock()) return false;
				return true;
			}
		}
		
		if (qty>product.getStock()) return false;
		return true;
	}
	
	public CartItem findById(Long id) {
		return cartItemRepository.findById(id).orElse(null);
	}

	public void removeCartItem(Long id) {
		cartItemRepository.deleteById(id);
	}
	
	public CartItem save(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}

	

}
