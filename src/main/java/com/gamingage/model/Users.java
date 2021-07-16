//entity class for user which include login and user info 

package com.gamingage.model;


import java.util.Date;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Users {  //table user is already reserved by database

	
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Long id;
		private String username;
		
		private String password;
		
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "dd-MMMM-yyyy")
		private Date createdDate;
		
		@Transient   //dont save to database, only use for validation
		private String confirmPassword;
		private String address;
		
		@Column(unique = true)
		private String email;
		private String phone;
		private boolean enabled;
		private String role;
		
		@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
		@JsonIgnore
		private ShoppingCart shoppingCart;
		
		public boolean isEnabled() {
			return enabled;
		}
		
		public Long getId() {
			return id;
		}
	
		public void setId(Long id) {
			this.id = id;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}


		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}


		public String getConfirmPassword() {
			return confirmPassword;
		}

		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public ShoppingCart getShoppingCart() {
			return shoppingCart;
		}

		public void setShoppingCart(ShoppingCart shoppingCart) {
			this.shoppingCart = shoppingCart;
		}



	

}