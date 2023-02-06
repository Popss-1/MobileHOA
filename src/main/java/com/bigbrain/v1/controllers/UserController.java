package com.bigbrain.v1.controllers;

import com.bigbrain.v1.models.Addresses;
import com.bigbrain.v1.models.Users;
import com.bigbrain.v1.serviceAndrepositories.AddressRepository;
import com.bigbrain.v1.serviceAndrepositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private AddressRepository addressRepository;
    private UsersRepository usersRepository;

    @Autowired // constructor dependency injection
    public UserController(UsersRepository usersRepository, AddressRepository addressRepository){
        this.usersRepository = usersRepository;
        this.addressRepository = addressRepository;
    }

    @GetMapping("/profile/{email}")
    public String ShowProfile(@PathVariable(value = "email") String email, Model model){
        Users user = usersRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/edit/{email}")
    public String EditProfile(@PathVariable(value = "email") String email, Model model){
        Users user = usersRepository.findByEmail(email);
        Addresses userAddress = addressRepository.findByUserID(user.getUserIdPK());
        model.addAttribute("userAddress", userAddress);
        model.addAttribute("user", user);
        //System.out.println("Pre profile: " + user.toString() + "\n" + userAddress.toString());
        return "editprofile";
    }

    @PostMapping("/profile/edit")
    public String SubmitProfileUpdate(@ModelAttribute("user") Users user, @ModelAttribute("userAddress") Addresses userAddress){
       // System.out.println("edit profile: " + user.toString() + "\n" + userAddress.toString());
        usersRepository.update(user, user.getUserIdPK());
        addressRepository.update(userAddress, userAddress.getUserIDFK());
        return "welcome";
    }


}
