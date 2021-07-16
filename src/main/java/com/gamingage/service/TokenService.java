//token creation class for email confirmation,password reset token 

package com.gamingage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamingage.model.Users;
import com.gamingage.model.Token;
import com.gamingage.repository.TokenRepository;

@Service
public class TokenService {

	@Autowired
	private TokenRepository tokenRepo;
	
	
	public void createMailConfirmationToken(Users user, String token) {
		Token myToken = new Token();
		myToken.setToken(token);
		myToken.setUser(user);
		tokenRepo.save(myToken);
	}
	
	public Token getToken(String token) {
		return tokenRepo.findByToken(token);
	}

	public void deleteToken(Token verificationToken) {
		tokenRepo.delete(verificationToken);
		
	}

	public void createPasswordResetToken(Users user, String token) {
		Token passwordResetToken = new Token();
		passwordResetToken.setUser(user);
		passwordResetToken.setToken(token);
		tokenRepo.save(passwordResetToken);
		
	}
	
	

}
