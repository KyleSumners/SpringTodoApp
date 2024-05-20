package com.kyle.springboot.todowebapp.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class LoginController {
	
	private AuthenticationService authenticationService;
	
	public LoginController(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}
	
	@GetMapping(value="login")
	public String gotoLoginPage() {
		return "login";
	}
	
	@PostMapping(value="login")
	public String gotoWelcomePage(@RequestParam String name, @RequestParam String password, ModelMap model) {
		if(authenticationService.authenticate(name, password)) {
			model.put("name", name);
			//Authentication
			// name - Kyle
			// password -dummy
			
			return "welcome";
		}
		model.put("errorMessage", "Incorrect username or password");
		return "login";
	}
	
	
}
