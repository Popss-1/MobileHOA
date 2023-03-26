package com.bigbrain.v1.controllers;

import com.bigbrain.v1.models.Addresses;
import com.bigbrain.v1.models.Incidents;
import com.bigbrain.v1.models.Users;
import com.bigbrain.v1.DAOandRepositories.IncidentRepository;
import com.bigbrain.v1.DAOandRepositories.UsersRepository;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Controller
public class IncidentController {

    private final String apiKey = "AIzaSyA_yaxjGDKLtw8qlGU1FsVTafhZwyYT2PI";
    private IncidentRepository incidentRepository;

    private UsersRepository usersRepository;

    @Autowired
    public IncidentController(IncidentRepository incidentRepository, UsersRepository usersRepository) {
        this.incidentRepository = incidentRepository;
        this.usersRepository = usersRepository;
    }

    //TODO delete
    @GetMapping("/incidentform")
    public String showIncidentForm( HttpSession httpSession, Model model){
       // System.out.println("Incident form: " + email);
        Users user = (Users ) httpSession.getAttribute("user");
        Incidents incident = new Incidents();
        incident.setUserIDFK(user.getUserIdPK());
        incident.setReportedByPhoneNumber(user.getPhoneNumber());
        model.addAttribute("newIncident", incident);
       // model.addAttribute("user", user);
        model.addAttribute("incidentAddress", new Addresses());
        return "incidentform";
    }

    @PostMapping("/incidentform")
    public String submitIncidentForm(HttpServletRequest request, @ModelAttribute("newIncident") Incidents newIncident, @ModelAttribute("incidentAddress") Addresses incidentAddress, Model model) throws IOException {
      // Store image into incidents obj
        //        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        MultipartFile file = multipartRequest.getFile("image");
//        byte[] imageData = file.getBytes();

        String address = incidentAddress.getAddressLine1() + " " + incidentAddress.getCity() + ", " + incidentAddress.getCity() + " " + incidentAddress.getZipCode();

        // geocode address
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context,
                    address).await();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newIncident.setLatitude(results[0].geometry.location.lat);
        newIncident.setLongitude(results[0].geometry.location.lng);

        LocalDate dateNow = LocalDate.now();
        newIncident.setIncidentDate(Date.valueOf(dateNow));

        //System.out.println("New incident:" + newIncident.toString());
        incidentRepository.save(newIncident);

        List<Incidents> allIncidents = incidentRepository.findAll();

        model.addAttribute("allIncidents", allIncidents);
     //   model.addAttribute("user", usersRepository.findById(newIncident.getUserIDFK()));
        return "incidentmap";
    }

    @GetMapping("/incidentmap")
    public String showIncidentMap(HttpSession httpSession, Model model){

        List<Incidents> allIncidents = incidentRepository.findAll();
        model.addAttribute("allIncidents", allIncidents);
      //  model.addAttribute("user", (Users) httpSession.getAttribute("user"));
        int [] stats = incidentStats();
        model.addAttribute("resolvedIncidents", stats[0]);
        model.addAttribute("newIncidents", stats[1]);
        return "incidentmap";
    }

    @GetMapping("/user/incidents")
    public String showUserIncidents(HttpSession httpSession, Model model){

        Users user = (Users) httpSession.getAttribute("user");
        List<Incidents> userIncidents = incidentRepository.findAllByID(user.getUserIdPK());
        //System.out.println("USERINCIDENTS: " + userIncidents);
        model.addAttribute("userIncidents", userIncidents);
        //model.addAttribute("user", user);
        return "userincidents";
    }

    @GetMapping("/user/deleteincidents/{incidentIDPK}")
    public String deleteIncidents(@PathVariable int incidentIDPK,HttpSession httpSession, Model model){
        incidentRepository.deleteById(incidentIDPK);
        List<Incidents> allIncidents = incidentRepository.findAll();
     //   model.addAttribute("user", (Users) httpSession.getAttribute("user"));
        model.addAttribute("allIncidents", allIncidents);
        return "incidentmap";
    }

    @GetMapping("/user/updateincident/{incidentIDPK}")
    public String updateIncident(@PathVariable int incidentIDPK, Model model, HttpSession httpSession){
        Incidents incidentToUpdate = incidentRepository.findIncidentByPK(incidentIDPK);
        model.addAttribute("user", (Users) httpSession.getAttribute("user"));
        model.addAttribute("incidentToUpdate", incidentToUpdate);
        model.addAttribute("incidentAddress", new Addresses());
        return "incidentupdateform";
    }

    @PostMapping("/user/updateincident")
    public String submitUpdateIncident(@ModelAttribute("incidentToUpdate") Incidents incidentToUpdate, HttpSession httpSession, @ModelAttribute("incidentAddress") Addresses incidentAddress, Model model){
        String address = incidentAddress.getAddressLine1() + " " + incidentAddress.getCity() + ", " + incidentAddress.getCity() + " " + incidentAddress.getZipCode();

        // geocode address
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
        GeocodingResult[] results;
        try {
            results = GeocodingApi.geocode(context,
                    address).await();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        incidentToUpdate.setLatitude(results[0].geometry.location.lat);
        incidentToUpdate.setLongitude(results[0].geometry.location.lng);


       // System.out.println("New incident:" + incidentToUpdate.toString());
        incidentRepository.updateById(incidentToUpdate, incidentToUpdate.getIncidentIDPK());

        List<Incidents> allIncidents = incidentRepository.findAll();


        model.addAttribute("allIncidents", allIncidents);
        model.addAttribute("user", (Users) httpSession.getAttribute("user"));
        return "incidentmap";
    }

    /*
    *Display stats for last month
    *Display number of total resolved and new incidents last month
     */
    public int [] incidentStats(){
        int [] arr = new int [2];
        int resolved_incidents = 0;
        int new_incidents = 0;

        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayOfLastMonth = LocalDate.of(currentDate.getYear(), currentDate.minusMonths(1).getMonth(), 1);
        LocalDate lastDayOfLastMonth = firstDayOfLastMonth.withDayOfMonth(firstDayOfLastMonth.lengthOfMonth());
        List<Incidents> lastMonthIncidents = incidentRepository.findByDateBetween(Date.valueOf(firstDayOfLastMonth), Date.valueOf(lastDayOfLastMonth));
        for ( Incidents incident : lastMonthIncidents){
            if ( incident.getStatus() == "New")
                new_incidents++;
            else
                resolved_incidents++;
        }
        arr[0] = resolved_incidents;
        arr[1] = new_incidents;
        return arr;
    }

}
