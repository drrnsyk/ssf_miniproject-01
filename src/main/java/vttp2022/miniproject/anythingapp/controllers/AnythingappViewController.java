package vttp2022.miniproject.anythingapp.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp2022.miniproject.anythingapp.models.Place;
import vttp2022.miniproject.anythingapp.services.AnythingappService;

@Controller
@RequestMapping("/view")
public class AnythingappViewController {
    
    @Autowired
    private AnythingappService anythingSvc;
    

    @GetMapping
    public String getAllEntries(Model model) {

        String userName = Place.userName;
        List<Place> places = new LinkedList<>();
        places = anythingSvc.getFromRepo(userName);
        model.addAttribute("userName", userName);
        model.addAttribute("places", places);
        return "view";

    }


}
