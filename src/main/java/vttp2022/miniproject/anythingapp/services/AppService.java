package vttp2022.miniproject.anythingapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.miniproject.anythingapp.models.App;
import vttp2022.miniproject.anythingapp.repositories.AppRepository;

@Service
public class AppService {

    @Autowired
    private AppRepository appRepo;
    
    public App generateEntry(String neighbourhood, String establishmentType, String establishmentName, String userName) {
        App entry = new App(neighbourhood, establishmentType, establishmentName, userName);
        return entry;
    }

    public void saveToRepo(App entry) {
        appRepo.saveToRedis(entry);
    }

}
