//custom serverside validation for user registration



package com.gamingage.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.gamingage.model.Users;
import com.gamingage.service.UserService;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Users.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Users user = (Users) target;
		
		 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.username.empty");
	     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email.empty");
	     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.empty");
	     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "error.confirmPassword.empty");
		 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "error.phone.empty");
		 
	        // Username must have from 4 characters to 32
	        if (user.getUsername().length() < 4) {
	            errors.rejectValue("username", "register.error.username.less.than.4");
	        }
	        if(user.getUsername().length() > 32){
	            errors.rejectValue("username","register.error.username.more.than.32");
	        }
	        //Email can't be duplicated
	        if (userService.findByEmail(user.getEmail()) != null){
	            errors.rejectValue("email", "register.error.duplicated.email");
	        }
	        //Password must have at least 8 characters and max 32
	        if (user.getPassword().length() < 8) {
	            errors.rejectValue("password", "register.error.password.less.than.8");
	        }
	        if (user.getPassword().length() > 32){
	            errors.rejectValue("password", "register.error.password.more.than.32");
	        }
	        if (!user.getPassword().equals(user.getConfirmPassword())){
	            errors.rejectValue("confirmPassword", "register.error.confirmPassword");
	        }
	        if (!user.getPhone().matches("([0-9 ]+)")){
	            errors.rejectValue("phone", "register.error.invalid.phone");
	        }

	}

}
