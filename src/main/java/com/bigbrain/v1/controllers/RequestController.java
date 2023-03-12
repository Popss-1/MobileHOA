package com.bigbrain.v1.controllers;

import com.bigbrain.v1.DAOandRepositories.MaintenanceRepository;
import com.bigbrain.v1.models.Addresses;
import com.bigbrain.v1.models.Maintenances;
import com.bigbrain.v1.models.Requests;
import com.bigbrain.v1.models.Users;
import com.bigbrain.v1.DAOandRepositories.AddressRepository;
import com.bigbrain.v1.DAOandRepositories.RequestRepository;
import com.bigbrain.v1.DAOandRepositories.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class RequestController {

    private RequestRepository requestRepository;
    private AddressRepository addressRepository;
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    public RequestController(RequestRepository requestRepository, AddressRepository addressRepository, MaintenanceRepository maintenanceRepository) {
        this.requestRepository = requestRepository;
        this.addressRepository = addressRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    @GetMapping("/user/requestform")
    public String showRequestForm(Model model, HttpSession httpSession){
        Users user = (Users) httpSession.getAttribute("user");
        Requests request = new Requests();
        request.setRequestUserIDFK(user.getUserIdPK());
        model.addAttribute("newRequest", request);
        model.addAttribute("user", user);
        return "submitrequestform";
    }

    @PostMapping("/user/requestform")
    public String submitRequestForm(@ModelAttribute("newRequest") Requests newRequest,HttpSession httpSession, Model model){
        Users user = (Users) httpSession.getAttribute("user");

        Addresses address = addressRepository.findByUserID(newRequest.getRequestUserIDFK());
        newRequest.setAddressIDFK(address.getAddressIDPK());
        // assign maintenance
        int maintenanceAssignment = assignMaintenance();
        if ( maintenanceAssignment != -1) {
            newRequest.setMaintenanceIdFK(maintenanceAssignment);
            newRequest.setStatus(Requests.Statuses.Assigned.toString());
        }
        else{
            newRequest.setStatus(Requests.Statuses.Received.toString());
        }
        requestRepository.save(newRequest);
        model.addAttribute("user", user);
        return "redirect:/welcome";
    }

    @GetMapping("/admin/alluserrequests")
    public String showAllUserRequests(HttpSession httpSession, Model model){
        Users user = (Users) httpSession.getAttribute("user");
        List<Requests> allUserRequests = requestRepository.findAllByUserIdFk(user.getUserIdPK());
       // System.out.println("allrequests: " + allUserRequests.toString());
        model.addAttribute("allUserRequests", allUserRequests);
        model.addAttribute("user", user);
        return "allrequests";
    }

    @GetMapping("/admin/deleterequest/{requestIDPK}")
    public String deleteRequest(@PathVariable int requestIDPK, Model model, HttpSession httpSession){
        Users user = (Users) httpSession.getAttribute("user");
        model.addAttribute("user", user);
        requestRepository.deleteById(requestIDPK);
        return "forward:/admin/alluserrequests/";
    }

    @GetMapping("/admin/updaterequest/{requestIDPK}")
    public String updateRequest(@PathVariable int requestIDPK, Model model, HttpSession httpSession){
        Requests requestToUpdate = requestRepository.findById(requestIDPK);
        Users user = (Users) httpSession.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("requestToUpdate", requestToUpdate);
        return "requestupdateform";
    }

    @PostMapping("/updaterequest")
    public String submitUpdateRequest(@ModelAttribute("requestToUpdate") Requests requestToUpdate, HttpSession httpSession,Model model){
        Users user = (Users) httpSession.getAttribute("user");
        requestRepository.update(requestToUpdate, requestToUpdate.getRequestIDPK());
        model.addAttribute("user", user);
        return "redirect:/admin/alluserrequests";
    }

    public int assignMaintenance(){
        List<Maintenances>allMaintenances = maintenanceRepository.findAll();
        for ( Maintenances maintenance : allMaintenances){
            if ( maintenance.getAvailability().equals("Available") && maintenance.getNumberOfRequests() <= 5)
                return maintenance.getMaintenanceIdPk();
        }
        return -1;
    }


}
