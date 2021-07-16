//class for sending email


package com.gamingage.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.gamingage.model.Users;


@Component
public class AdminVerificationMailSender {
	
	@Value("${aws.ses.mail}")
	private String senderMail;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public SimpleMailMessage confirmationMail(Users user, String password) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setSubject("Moderator Account Registeration");
		mail.setText("\n Hi new moderator account was created with the following information\n"+
				"\n username : "+user.getUsername()+
				"\n email        : "+user.getEmail()+
				"\n address    : "+user.getAddress()+
				"\n phone       : "+user.getPhone()+
				"\n\n Please use the following password to login."+
				"\n password  : "+password
				);
		mail.setFrom(senderMail);
		
		return mail;
	}
}