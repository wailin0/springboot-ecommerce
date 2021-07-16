package com.gamingage.model;



import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ShoppingCart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private BigDecimal grandTotal;
	
	@OneToMany(mappedBy="shoppingCart", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<CartItem> cartItemList;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Users user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public List<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

}
