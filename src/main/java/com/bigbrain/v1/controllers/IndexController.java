package com.bigbrain.v1.controllers;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import com.bigbrain.v1.models.Users;
import com.bigbrain.v1.serviceAndrepositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Identify controller

//After logged in
@Controller
public class IndexController {

	@Autowired
	private UsersRepository usersRepository;

	// Redirect to home after google login
	// Get data from google login
	// feed data to /home for welcome message
//	@RequestMapping("/home")
//	public Map<String, Object> currentUser(@AuthenticationPrincipal OAuth2User authenticatedUser) {
//		Users user = usersRepository.findByEmail(authenticatedUser.getAttribute("email"));
////		// user is registered
////		if( user != null){
////
////		}
//		return Collections.singletonMap("name", authenticatedUser.getAttribute("name"));
//	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexPage(){
		return "index";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcomePage() {
		return "welcome";
	}


}
