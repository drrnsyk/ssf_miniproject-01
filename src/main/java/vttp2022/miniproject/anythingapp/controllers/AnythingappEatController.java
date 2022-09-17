package vttp2022.miniproject.anythingapp.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.miniproject.anythingapp.models.Place;
import vttp2022.miniproject.anythingapp.services.AnythingappService;

@Controller
@RequestMapping("/eat")
public class AnythingappEatController {
    
    @Autowired
    private AnythingappService anythingSvc;

    @GetMapping
    public String randomGeneratorPage(Model model) {

        String userName = Place.userName;
        model.addAttribute("userName", userName);
        return "eat";

    }
    

    @GetMapping(value="/random")
    public String generateRandom(@RequestParam String neighbourhood, @RequestParam String establishmentType, Model model) {

        String userName = Place.userName;
        List<Place> places = new LinkedList<>();
        places = anythingSvc.getFromRepo(userName);
        List<Place> filteredPlaces = new LinkedList<>();
        // filteredPlaces = places;
        // System.out.println(neighbourhood);
        // System.out.println(establishmentType);

        for (int i = 0; i < places.size(); i++) {
            if (neighbourhood.equalsIgnoreCase("nil") && establishmentType.equalsIgnoreCase("nil")) {
                filteredPlaces.add(places.get(i));
            } else if (neighbourhood.equalsIgnoreCase("nil")) {
                if (places.get(i).getEstablishmentType().equalsIgnoreCase(establishmentType)) {
                    filteredPlaces.add(places.get(i));
                }
            } else if (establishmentType.equalsIgnoreCase("nil")) {
                if (places.get(i).getNeighbourhood().equalsIgnoreCase(neighbourhood)) {
                    filteredPlaces.add(places.get(i));
                }
            }
        }

        Random rand = new Random();
        int upperbound = filteredPlaces.size();
        int intRand = rand.nextInt(upperbound);

        System.out.println(intRand);

        Place randomPlace = filteredPlaces.get(intRand);

        model.addAttribute("userName", userName);
        model.addAttribute("randomPlace", randomPlace);

        return "eatsuccess";

    }
    
}
