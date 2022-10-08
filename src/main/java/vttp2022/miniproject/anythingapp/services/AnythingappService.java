package vttp2022.miniproject.anythingapp.services;

import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.miniproject.anythingapp.models.Contribution;
import vttp2022.miniproject.anythingapp.models.Place;
import vttp2022.miniproject.anythingapp.models.Review;
import vttp2022.miniproject.anythingapp.repositories.AnythingappRepository;

@Service
public class AnythingappService {

    @Autowired
    private AnythingappRepository anythingRepo;

     // API call from tih stb
     public static final String URL = "https://tih-api.stb.gov.sg/content/v1/food-beverages/search";

     // inject in the key
     @Value("${API_KEY}")
     private String key;

     public List<Review> getReviews(String establishmentName) {

        // System.out.println("get review" + " " + establishmentName);

        String payloadStr;

        // System.out.println("Getting news Articles from CryptoCompare.com");

        try {
            String url = UriComponentsBuilder.fromUriString(URL)
                            .queryParam("keyword", URLEncoder.encode(establishmentName, "UTF-8"))
                            .queryParam("apikey", key)
                            .toUriString();

            // create a request entity
            // create the GET request, GET url
            RequestEntity<Void> req = RequestEntity.get(url).build();
            // to make the call to cryptocompare
            // need to create restTemplate
            RestTemplate template = new RestTemplate();
            // make the call
            ResponseEntity<String> resp;
            resp = template.exchange(req, String.class);
            // get the payload and do something with it
            payloadStr = resp.getBody();
            // prints out the payload 
            //System.out.println("payload from API: " + payloadStr);
            // prints out the query string
            // System.out.println("query string: " + language);

        } catch (Exception ex) {
            System.err.printf("Error: %s\n", ex.getMessage());
            return Collections.emptyList();
        }

        // convert payload string (json string) to json object
        // create a StringReader to read the payload string
        Reader strReader = new StringReader(payloadStr);
        // create a JsonReader to read the StringReader
        JsonReader jsonReader = Json.createReader(strReader);
        // read the payload as a json object (entire API result)
        JsonObject payloadJsonObject = jsonReader.readObject();
        // get the array from the json object (entired array from the data portion)
        JsonArray dataArray = payloadJsonObject.getJsonArray("data");

        // print out data size to check content
        // System.out.println("This is the size of the data json array:" + dataArray.size());
        // System.out.println(dataArray.get(0));

        JsonObject data = dataArray.getJsonObject(0);
        JsonArray dataReviews = data.getJsonArray("reviews");

        String reviewEstablishmentName = data.getString("name");
        String overallRating = String.valueOf(data.getInt("rating"));

        // instantiate a list to store the data
        List<Review> list = new LinkedList<>();

        for (int i = 0; i < dataReviews.size(); i++) {
            Review review = new Review();
            JsonObject jo = dataReviews.getJsonObject(i);
            review.setEstablishmentName(reviewEstablishmentName);
            review.setOverallRating(overallRating);
            review.setAuthorName(jo.getString("authorName"));
            review.setAuthorReview(jo.getString("text"));
            review.setAuthorRating(String.valueOf(jo.getInt("rating")));
            review.setTime(jo.getString("time"));
            review.setProfilePhotoUrl(jo.getString("profilePhoto"));
            list.add(review);
        }

        // System.out.println("This is the size of list of reviews: " + list.size());

        return list;
        
    }
 
    
    public Place generatePlace(String neighbourhood, String establishmentType, String establishmentName) {
        Place place = new Place(neighbourhood, establishmentType, establishmentName);
        return place;
    }

    public void saveToRepo(Place place, String userName) {
        anythingRepo.saveToRedis(place, userName);
    }

    public List<Place> getFromRepo(String userName) {
        Optional<List<Place>> opt = anythingRepo.getFromRedis(userName);
        if (opt.isEmpty()) // if the result is empty
            return List.of(); // returns an empty list 
        return opt.get();
    }

    public Place findById(String id, List<Place> places) {

        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).getId().equalsIgnoreCase(id)) {
                return places.get(i);
            }
        }

        return null;

    }

    public void updateToRepo(List<Place> places, String userName) {
        anythingRepo.updateToRedis(places, userName);
    }

    public Contribution generateContribution(String establishmentName, String establishmentType, String rating, String review, String address, String postal, String name, String source) {
        Contribution contribution = new Contribution(establishmentName, establishmentType, rating, review, address, postal, name, source);
        return contribution;
    }

}
