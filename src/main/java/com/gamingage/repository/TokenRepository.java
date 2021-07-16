package com.gamingage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamingage.model.Users;
import com.gamingage.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
	
	Token findByUser(Users user);
	Token findByToken(String token);


}
