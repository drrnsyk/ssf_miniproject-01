package vttp2022.miniproject.anythingapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.miniproject.anythingapp.models.Place;
import vttp2022.miniproject.anythingapp.repositories.AnythingappRepository;

@Service
public class AnythingappService {

    @Autowired
    private AnythingappRepository anythingRepo;
    
    public Place generatePlace(String neighbourhood, String establishmentType, String establishmentName) {
        Place place = new Place(neighbourhood, establishmentType, establishmentName);
        return place;
    }

    public void saveToRepo(Place place, String userName) {
        anythingRepo.saveToRedis(place, userName);
    }

    public List<Place> getFromRepo(String userName) {
        Optional<List<Place>> opt = anythingRepo.getFromRedis(userName);
        return opt.get();
    }

}
