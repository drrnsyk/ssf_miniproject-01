package vttp2022.miniproject.anythingapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {
    
    @GetMapping
    public String getApp(Model model) {
        
        return "track";
    }

}
