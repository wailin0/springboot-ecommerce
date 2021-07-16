package com.gamingage.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.gamingage.model.Users;

@Entity
public class Token {
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	private String token;
	
	@OneToOne(targetEntity = Users.class,fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private Users user;

	
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public Users getUser() {
		return user;
	}


	public void setUser(Users user) {
		this.user = user;
	}




}
