package vttp2022.miniproject.anythingapp.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class App {
    
    String neighbourhood;
    String establishmentType;
    String establishmentName;
    String userName;

    public App(String neighbourhood, String establishmentType, String establishmentName, String userName) {
        this.neighbourhood = neighbourhood;
        this.establishmentType = establishmentType;
        this.establishmentName = establishmentName;
        this.userName = userName;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getEstablishmentType() {
        return establishmentType;
    }

    public void setEstablishmentType(String establishmentType) {
        this.establishmentType = establishmentType;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // to create jsonObject from model
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("neighbourhood", neighbourhood)
                .add("establishmentType", establishmentType)
                .add("establishmentName", establishmentName)
                .add("username", userName)
                .build();
    }

}
