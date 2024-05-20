package com.kyle.springboot.todowebapp.login;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	public boolean authenticate(String username, String password) {
		boolean isValidUsername = username.equals("Kyle");
		boolean isValidPassword = password.equals("dummy");
		
		return isValidUsername && isValidPassword;
	}
}
