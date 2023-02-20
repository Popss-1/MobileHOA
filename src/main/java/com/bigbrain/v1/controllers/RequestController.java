package com.bigbrain.v1.controllers;

import com.bigbrain.v1.models.Addresses;
import com.bigbrain.v1.models.Incidents;
import com.bigbrain.v1.models.Requests;
import com.bigbrain.v1.models.Users;
import com.bigbrain.v1.serviceAndrepositories.AddressRepository;
import com.bigbrain.v1.serviceAndrepositories.IncidentRepository;
import com.bigbrain.v1.serviceAndrepositories.RequestRepository;
import com.bigbrain.v1.serviceAndrepositories.UsersRepository;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class RequestController {

    private RequestRepository requestRepository;
    private UsersRepository usersRepository;
    private AddressRepository addressRepository;

    @Autowired
    public RequestController(RequestRepository requestRepository, UsersRepository usersRepository, AddressRepository addressRepository){
        this.requestRepository = requestRepository;
        this.usersRepository = usersRepository;
        this.addressRepository = addressRepository;
    }

    @GetMapping("/requestform/{email}")
    public String showRequestForm(@PathVariable("email") String email, Model model){
        Users user = usersRepository.findByEmail(email);
        Requests request = new Requests();
        request.setRequestUserIDFK(user.getUserIdPK());
        model.addAttribute("newRequest", request);
        model.addAttribute("user", user);
        return "submitrequest";
    }

    @PostMapping("/requestform")
    public String submitRequestForm(@ModelAttribute("newRequest") Requests newRequest, Model model){
        Users user = usersRepository.findById(newRequest.getRequestUserIDFK());
        newRequest.setStatus(String.valueOf(Requests.Statuses.Assigned));
        Addresses address = addressRepository.findByUserID(newRequest.getRequestUserIDFK());
        newRequest.setAddressIDFK(address.getAddressIDPK());
        LocalDate dateNow = LocalDate.now();
        newRequest.setRequestDate(Date.valueOf(dateNow));

        // assign MTM
        System.out.println("newrequest: " + newRequest.toString());
        requestRepository.save(newRequest);
        return "redirect:alluserrequests/" + user.getEmail();
    }

    @GetMapping("/alluserrequests/{email}")
    public String showAllUserRequests(@PathVariable( "email") String email, Model model){
        Users user = usersRepository.findByEmail(email);
        List<Requests> allUserRequests = requestRepository.findAllByUserIdFk(user.getUserIdPK());
       // System.out.println("allrequests: " + allUserRequests.toString());
        model.addAttribute("allUserRequests", allUserRequests);
        model.addAttribute("user", user);
        return "userrequests";
    }

    @GetMapping("/deleterequest/{requestIDPK}")
    public String deleteRequest(@PathVariable int requestIDPK, Model model){
        Requests request = requestRepository.findById(requestIDPK);
        Users user = usersRepository.findById(request.getRequestUserIDFK());
        requestRepository.deleteById(requestIDPK);
        return "forward:/alluserrequests/" + user.getEmail();
    }

    @GetMapping("/updaterequest/{requestIDPK}")
    public String updateRequest(@PathVariable int requestIDPK, Model model){
        Requests requestToUpdate = requestRepository.findById(requestIDPK);
        Users user = usersRepository.findById(requestToUpdate.getRequestUserIDFK());
        model.addAttribute("user", user);
        model.addAttribute("requestToUpdate", requestToUpdate);
        return "requestupdateform";
    }

    @RequestMapping(value = "/updaterequest", method = RequestMethod.POST)
    public String submitUpdateRequest(@ModelAttribute("requestToUpdate") Requests requestToUpdate, Model model){
        Users user = usersRepository.findById(requestToUpdate.getRequestUserIDFK());
        requestRepository.update(requestToUpdate, requestToUpdate.getRequestIDPK());
        model.addAttribute("user", user);
        return "redirect:alluserrequests/" + user.getEmail();
    }


}
