package vttp2022.miniproject.anythingapp.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.miniproject.anythingapp.models.Place;
import vttp2022.miniproject.anythingapp.services.AnythingappService;

@Controller
@RequestMapping("/track")
public class AnythingappTrackController {

    @Autowired
    private AnythingappService anythingSvc;

    @GetMapping
    public String getUserName(@RequestParam String userName, Model model) {
        Place.userName = userName;
        model.addAttribute("userName", userName);
        return "track";
    }
    
    @PostMapping
    public String submitEntry(@RequestBody MultiValueMap<String, String> form, Model model) {
        
        // for testing and checking
        System.out.println(form.getFirst("neighbourhood"));
        System.out.println(form.getFirst("establishmentType"));
        System.out.println(form.getFirst("establishmentName"));
        System.out.println(form.getFirst("userName"));
        System.out.println("----------");

        // extract parameters from form
        String neighbourhood = form.getFirst("neighbourhood");
        String establishmentType = form.getFirst("establishmentType");
        String establishmentName = form.getFirst("establishmentName");
        String userName = form.getFirst("userName");

        // inject the userName into track.html
        model.addAttribute("userName", userName);

        // generate the place model and send to repo 
        anythingSvc.saveToRepo(anythingSvc.generatePlace(neighbourhood, establishmentType, establishmentName), userName);
        
        // retrive list of places from database
        List<Place> places = new LinkedList<>();
        places = anythingSvc.getFromRepo(userName);
        // filter to only display recent three places to be injected
        List<Place> recentThreePlaces = new LinkedList<>();
        long size = places.size();
        long recentThree = 3;
        if (size < 3) {
            recentThree = size;
        }
        for (int i = 0; i < recentThree; i++) {
            recentThreePlaces.add(places.get(i));
        }
        model.addAttribute("recentThreePlaces", recentThreePlaces);

        return "logsuccess";

    }

}
