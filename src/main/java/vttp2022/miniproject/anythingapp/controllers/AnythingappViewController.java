package vttp2022.miniproject.anythingapp.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp2022.miniproject.anythingapp.models.Place;
import vttp2022.miniproject.anythingapp.models.Review;
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

    @GetMapping(value="/edit/{id}")
    public String editEntryPage(@PathVariable(value="id") String id, Model model) {
        
        String userName = Place.userName;
        List<Place> places = new LinkedList<>();
        places = anythingSvc.getFromRepo(userName);
        System.out.println("This is the size of list of places retrived: " + places.size());

        Place place = anythingSvc.findById(id,places);
        System.out.println("Place retrived: " + place.getId());
        System.out.println("Place retrived: " + place.getEstablishmentName());
        model.addAttribute("userName", userName);
        model.addAttribute("place", place);
        return "edit";
    } 

    @PostMapping(value="/editsuccess")
    public String submitEntry(@RequestBody MultiValueMap<String, String> form, Model model) {
        // for testing and checking
        System.out.println("This is from the view controller, edit entry: ");
        System.out.println(form.getFirst("id"));
        System.out.println(form.getFirst("neighbourhood"));
        System.out.println(form.getFirst("establishmentType"));
        System.out.println(form.getFirst("establishmentName"));
        System.out.println(form.getFirst("userName"));
        System.out.println("----------");

        // extract parameters from form
        String id = form.getFirst("id");
        String neighbourhood = form.getFirst("neighbourhood");
        String establishmentType = form.getFirst("establishmentType");
        String establishmentName = form.getFirst("establishmentName");
        String userName = form.getFirst("userName");

        // retrive list of place from redis
        List<Place> places = new LinkedList<>();
        places = anythingSvc.getFromRepo(userName);
        // search through the list to find the id of place that was edited
        int index = -1;
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).getId().equalsIgnoreCase(id)) {
                index = i;
            } else {
                System.out.println("No match found");
            }
        }
        // update the match entry with the new changes
        places.get(index).setNeighbourhood(neighbourhood);
        places.get(index).setEstablishmentType(establishmentType);
        places.get(index).setEstablishmentName(establishmentName);

        // delete existing records from redis and save updated list into redis
        anythingSvc.updateToRepo(places, userName);

        // testing to inject
        model.addAttribute("userName", userName);
        model.addAttribute("places", places);

        return "redirect:/view";
    }

    @GetMapping(value="/delete/{id}")
    public String deleteEntry(@PathVariable(value="id") String id, Model model) {

        String userName = Place.userName;
        List<Place> places = new LinkedList<>();
        places = anythingSvc.getFromRepo(userName);
        // search through the list to find the id of place to be deleted
        int index = -1;
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).getId().equalsIgnoreCase(id)) 
                index = i;
        }

        // remove the place from the list
        places.remove(index);
        // save list back to redis
        anythingSvc.updateToRepo(places, userName);

        // testing to inject
        model.addAttribute("userName", userName);
        model.addAttribute("places", places);
        
        return "redirect:/view";
    }

    @GetMapping(value="/reviews/{establishmentName}")
    public String getReviews(@PathVariable(value="establishmentName") String establishmentName, Model model) {

        Boolean isSameEstablishment = false;
        String userName = Place.userName;
        String establishmentType = "";
        List<Review> listOfReviews = new LinkedList<>();
        listOfReviews = anythingSvc.getReviews(establishmentName);
        List<Place> places = new LinkedList<>();
        places = anythingSvc.getFromRepo(userName);
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).getEstablishmentName().equalsIgnoreCase(establishmentName)) {
                establishmentType = places.get(i).getEstablishmentType();
            }
        }

        if (listOfReviews.get(0).getEstablishmentName().equalsIgnoreCase(establishmentName))
            isSameEstablishment = true;

        model.addAttribute("userName", userName);
        model.addAttribute("establishmentName", establishmentName);
        model.addAttribute("establishmentType", establishmentType);
        model.addAttribute("isSameEstablishment", isSameEstablishment);
        model.addAttribute("listOfReviews", listOfReviews);
        model.addAttribute("overallRating", listOfReviews.get(0).getOverallRating());
       
        return "review";

    }

}
