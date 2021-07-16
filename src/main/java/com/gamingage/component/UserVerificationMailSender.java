//class for sending email


package com.gamingage.component;

import java.util.Locale;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.gamingage.model.Order;
import com.gamingage.model.Users;


@Component
public class UserVerificationMailSender {

	@Value("${aws.ses.mail}")
	private String senderMail;

	@Autowired
	private TemplateEngine templateEngine;
	
	public SimpleMailMessage confirmationMail(String link,Users customer ) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(customer.getEmail());
		mail.setSubject("Email Confirmation");
		mail.setText("\n Dear "+customer.getUsername()+  ",\n\n\nThanks for creating GamingAge Shopping Account \n  Please click the link below to verify your email address\n" + link
				);
		mail.setFrom(senderMail);
		
		return mail;
	}
	
	
	public SimpleMailMessage passwordResetTokenEmail(String link, String email, Users user) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setSubject("Password Reset Mail");
		mail.setText("\n Hi "+user.getUsername()+ ",\n\n\n Did you just request to reset your password?\n  If yes, please use the link below to reset your password \n "+link);
		mail.setFrom(senderMail);
		
		return mail;
	}
	
	
	public MimeMessagePreparator OrderConfirmationEmail (Users user, Order order, Locale locale) {
		Context context = new Context();
		context.setVariable("order", order);
		context.setVariable("user", user);
		context.setVariable("cartItemList", order.getCartItemList());
		String text = templateEngine.process("orderEmailTemplate", context);
		
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setTo(user.getEmail());
				email.setSubject("Order Confirmation - "+order.getId());
				email.setText(text, true);
				email.setFrom(new InternetAddress(senderMail));
			}
		};
		
		return messagePreparator;
	}


	public SimpleMailMessage emailUpdateTokenEmail(String link, String email, Users user) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setSubject("Email Change Confirmation");
		mail.setText("\n Hi "+user.getUsername()+ ",\n\n\n Did you just request to change your email?\n  If yes, please use the link below to activate your new email \n "+link);
		mail.setFrom(senderMail);
		
		return mail;
	}


}
