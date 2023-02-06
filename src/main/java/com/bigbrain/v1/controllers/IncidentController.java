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
import de.pentabyte.googlemaps.Location;
import de.pentabyte.googlemaps.StaticMap;
import de.pentabyte.googlemaps.StaticMarker;
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
    public String IncidentForm(@PathVariable(value = "email") String email, Model model){
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
    public String SubmitIncidentForm(@ModelAttribute("newIncident") Incidents newIncident, @ModelAttribute("incidentAddress") Addresses incidentAddress, Model model){
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

        model.addAttribute("staticMapUrl", generateStaticMap(allIncidents));
        model.addAttribute("allIncidents", allIncidents);
        model.addAttribute("user", usersRepository.findById(newIncident.getUserIDFK()));
        return "incidentmap";
    }

    @GetMapping("/incidentmap/{email}")
    public String showIncidentMap(@PathVariable(value = "email") String email, Model model){

        List<Incidents> allIncidents = incidentRepository.findAll();
        model.addAttribute("staticMapUrl", generateStaticMap(allIncidents));
        model.addAttribute("allIncidents", allIncidents);
        model.addAttribute("user", usersRepository.findByEmail(email));
        return "incidentmap";
    }

    @GetMapping("/userincidents/{email}")
    public String showUserIncidents(@PathVariable(value = "email") String email, Model model){
        return "userincidents";
    }

    //TODO delete incident

    //TODO update incident

    // Create static map URL
    private String generateStaticMap(List<Incidents> allIncidents){
        // Create static map
        StaticMap sm = new StaticMap(1000,1000, apiKey);
        // Set map to be at the center of the community
        sm.setLocation(new Location(40.210108, -76.775418), 17);
        int incidentCount = 1;
        for(Incidents in: allIncidents){
            StaticMarker staticMarker = new StaticMarker(in.getLatitude(), in.getLongitude());
            staticMarker.setLabel(Character.forDigit(incidentCount, 10));
            sm.addMarker(staticMarker);// max 9 incidents
            //System.out.println("Staticmarker " + incidentCount + ": " + staticMarker.toString());
            incidentCount++;
        }
        return sm.toString();
    }
}
