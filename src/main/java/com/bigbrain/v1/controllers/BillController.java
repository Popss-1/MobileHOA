package com.bigbrain.v1.controllers;

import com.bigbrain.v1.serviceAndrepositories.UsersRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillController {

    private UsersRepository usersRepository;


    // run prorated bill generation
    @GetMapping("/batch/new/{email}")
    public void generateNewUserBill(@PathVariable(value = "email") String email){

    }

    // run monthly bill generation
    @GetMapping("/batch/existing/{email}")
    public void generateExistingUserBill(@PathVariable(value = "email") String email){

    }
}
