package vttp2022.miniproject.anythingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp2022.miniproject.anythingapp.models.Contribution;
import vttp2022.miniproject.anythingapp.services.AnythingappService;

@RestController
@RequestMapping("/submit")
public class AnythingappRestController {

    @Autowired AnythingappService anythingappSvc;
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String submitEntry (@RequestBody MultiValueMap<String, String> form) {

        String establishmentName = form.getFirst("establishmentName");
        String establishmentType = form.getFirst("establishmentType");
        String rating = form.getFirst("rating");
        String review = form.getFirst("review");
        String address = form.getFirst("address");
        String postal = form.getFirst("postal");
        String name = form.getFirst("name");
        String source = form.getFirst("source");

        Contribution contribution = anythingappSvc.generateContribution(establishmentName, establishmentType, rating, review, address, postal, name, source);

        return contribution.toJson().toString();
    }
    
}
