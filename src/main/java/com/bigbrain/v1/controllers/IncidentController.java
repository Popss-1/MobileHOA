package com.bigbrain.v1.controllers;

import com.bigbrain.v1.models.Addresses;
import com.bigbrain.v1.models.Incidents;
import com.bigbrain.v1.models.Users;
import com.bigbrain.v1.serviceAndrepositories.IncidentRepository;
import com.bigbrain.v1.serviceAndrepositories.UsersRepository;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/incidentform/{email}")
    public String showIncidentForm(@PathVariable(value = "email") String email, Model model){
       // System.out.println("Incident form: " + email);
        Users user = usersRepository.findByEmail(email);
        Incidents incident = new Incidents();
        incident.setUserIDFK(user.getUserIdPK());
        incident.setReportedByPhoneNumber(user.getPhoneNumber());
        model.addAttribute("newIncident", incident);
        model.addAttribute("user", user);
        model.addAttribute("incidentAddress", new Addresses());
        return "incidentform";
    }

    @PostMapping("/incidentform")
    public String submitIncidentForm(@ModelAttribute("newIncident") Incidents newIncident, @ModelAttribute("incidentAddress") Addresses incidentAddress, Model model){
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

      //  model.addAttribute("staticMapUrl", generateStaticMap(allIncidents));
        model.addAttribute("allIncidents", allIncidents);
        model.addAttribute("user", usersRepository.findById(newIncident.getUserIDFK()));
        return "incidentmap";
    }

    @GetMapping("/incidentmap/{email}")
    public String showIncidentMap(@PathVariable(value = "email") String email, Model model){

        List<Incidents> allIncidents = incidentRepository.findAll();
      //  model.addAttribute("staticMapUrl", generateStaticMap(allIncidents));
        model.addAttribute("allIncidents", allIncidents);
        model.addAttribute("user", usersRepository.findByEmail(email));
        return "incidentmap";
    }

    @GetMapping("/userincidents/{email}")
    public String showUserIncidents(@PathVariable(value = "email") String email, Model model){
        Users user = usersRepository.findByEmail(email);
        List<Incidents> userIncidents = incidentRepository.findAllByID(user.getUserIdPK());
        //System.out.println("USERINCIDENTS: " + userIncidents);
        model.addAttribute("userIncidents", userIncidents);
        model.addAttribute("user", user);
        return "userincidents";
    }

    @GetMapping("/deleteincidents/{incidentIDPK}")
    public String deleteIncidents(@PathVariable int incidentIDPK, Model model){
        Incidents incident = incidentRepository.findIncidentByPK(incidentIDPK);
        incidentRepository.deleteById(incidentIDPK);
        List<Incidents> allIncidents = incidentRepository.findAll();
        Users user = usersRepository.findById(incident.getUserIDFK());
        model.addAttribute("user", user);
        model.addAttribute("allIncidents", allIncidents);
        return "incidentmap";
    }

    @GetMapping("/updateincident/{incidentIDPK}")
    public String updateIncident(@PathVariable int incidentIDPK, Model model){
        Incidents incidentToUpdate = incidentRepository.findIncidentByPK(incidentIDPK);
        Users user = usersRepository.findById(incidentToUpdate.getUserIDFK());
        model.addAttribute("user", user);
        model.addAttribute("incidentToUpdate", incidentToUpdate);
        model.addAttribute("incidentAddress", new Addresses());
        return "incidentupdateform";
    }

    @PostMapping("/updateincident")
    public String submitUpdateIncident(@ModelAttribute("incidentToUpdate") Incidents incidentToUpdate, @ModelAttribute("incidentAddress") Addresses incidentAddress, Model model){
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


        System.out.println("New incident:" + incidentToUpdate.toString());
        incidentRepository.updateById(incidentToUpdate, incidentToUpdate.getIncidentIDPK());

        List<Incidents> allIncidents = incidentRepository.findAll();


        model.addAttribute("allIncidents", allIncidents);
        model.addAttribute("user", usersRepository.findById(incidentToUpdate.getUserIDFK()));
        return "incidentmap";
    }

}
