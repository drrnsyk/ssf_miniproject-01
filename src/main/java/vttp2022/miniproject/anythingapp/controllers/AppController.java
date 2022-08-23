package vttp2022.miniproject.anythingapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp2022.miniproject.anythingapp.services.AppService;

@Controller
@RequestMapping("/track")
public class AppController {

    @Autowired
    private AppService appSvc;

    @PostMapping
    public String submitLog(@RequestBody MultiValueMap<String, String> form, Model model) {

        // for testing and checking
        System.out.println(form.getFirst("neighbourhood"));
        System.out.println(form.getFirst("establishmentType"));
        System.out.println(form.getFirst("establishmentName"));
        System.out.println(form.getFirst("userName"));

        // extract parameters from form
        String neighbourhood = form.getFirst("neighbourhood");
        String establishmentType = form.getFirst("establishmentType");
        String establishmentName = form.getFirst("establishmentName");
        String userName = form.getFirst("userName");

        // generate the entry model and send to repo 
        appSvc.saveToRepo(appSvc.generateEntry(neighbourhood, establishmentType, establishmentName, userName));



        return "logsuccess";

    }

}
