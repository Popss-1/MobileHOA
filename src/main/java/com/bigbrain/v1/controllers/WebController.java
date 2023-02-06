package com.bigbrain.v1.controllers;

import com.bigbrain.v1.models.Addresses;
import com.bigbrain.v1.models.Users;
import com.bigbrain.v1.serviceAndrepositories.AddressRepository;
import com.bigbrain.v1.serviceAndrepositories.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//Identify controller

//After logged in
@Controller
public class WebController {

    private AddressRepository addressRepository;
    private UsersRepository usersRepository;

    @Autowired // constructor dependency injection
    public WebController(UsersRepository usersRepository, AddressRepository addressRepository){
        this.usersRepository = usersRepository;
        this.addressRepository = addressRepository;
    }


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

    @RequestMapping(value = "/")
    public String indexPage() {
        return "index";
    }


    @RequestMapping(value =  "/welcome")
    public String postLogin(@AuthenticationPrincipal OAuth2User principal, ModelMap model, HttpSession httpSession){
        String email = principal.getAttribute("email");
        Users user = usersRepository.findByEmail(email);
        System.out.println("Found user: " + user);
        if ( user == null){
            String firstName = principal.getAttribute("given_name");
            String lastName = principal.getAttribute("family_name");
            Users newUser = new Users(email, firstName, lastName);

            model.addAttribute("newUser", newUser);
            System.out.println("NEW USER: " + newUser.toString());
            model.addAttribute("newAddress", new Addresses()); // adduserIDFK
            return "registration";
        }

        model.addAttribute("user", user);
        httpSession.setAttribute("user", user);
        Addresses address = addressRepository.findByUserID(user.getUserIdPK());
        httpSession.setAttribute("userAddress", address);
        return "welcome";
    }

    // Registration form submission
    @RequestMapping(value =  "/registration", method = RequestMethod.POST)
    public String submitRegistration(@ModelAttribute("newUser") Users newUser, @ModelAttribute("newAddress") Addresses newAddress, HttpSession httpSession, ModelMap model){

        newUser.setAddress(newAddress);
        newUser.setSubscriptionStatus(Users.SubscriptionStatues.Pending.toString());
        newUser.setRole(Users.Roles.Homeowner);
        usersRepository.save(newUser);
        Users user = usersRepository.findByEmail(newUser.getEmail());
        System.out.println("Form submission newUser: " + user.toString());
        newAddress.setUserIDFK(user.getUserIdPK());
        user.setAddress(newAddress);
        System.out.println("Form submission: " + user.toString() + newAddress.toString());
        httpSession.setAttribute("user", user);
        model.addAttribute("user", user);
        addressRepository.save(newAddress);
        return "welcome";
    }

    @RequestMapping(value =  "/registration")
    public String showRegistrationForm(){
        return "registration";
    }


    // Feed princiapl user to profile to load the profile
    @RequestMapping(value = "/profile")
    public String profilePage() {
        return "profile";
    }


}
