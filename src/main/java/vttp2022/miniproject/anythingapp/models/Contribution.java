package vttp2022.miniproject.anythingapp.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Contribution {

    String establishmentName;
    String establishmentType;
    String rating;
    String review;
    String address;
    String postal;
    String name;
    String source;

    public String getEstablishmentName() {
        return establishmentName;
    }
    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }
    public String getEstablishmentType() {
        return establishmentType;
    }
    public void setEstalishmentType(String establishmentType) {
        this.establishmentType = establishmentType;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPostal() {
        return postal;
    }
    public void setPostal(String postal) {
        this.postal = postal;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public Contribution(String establishmentName, String establishmentType, String rating, String review, String address,
            String postal, String name, String source) {
        this.establishmentName = establishmentName;
        this.establishmentType = establishmentType;
        this.rating = rating;
        this.review = review;
        this.address = address;
        this.postal = postal;
        this.name = name;
        this.source = source;
    }

    // to create jsonObject from model
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("establishmentName", establishmentName)
                .add("establishmentType", establishmentType)
                .add("rating", rating)
                .add("review", review)
                .add("address", address)
                .add("postal", postal)
                .add("name", name)
                .add("source", source)
                .build();
    }
    
    
}

