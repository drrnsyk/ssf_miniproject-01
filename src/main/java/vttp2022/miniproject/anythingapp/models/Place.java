package vttp2022.miniproject.anythingapp.models;

import java.io.Reader;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Place {
    
    String neighbourhood;
    String establishmentType;
    String establishmentName;

    public Place(String neighbourhood, String establishmentType, String establishmentName) {
        this.neighbourhood = neighbourhood;
        this.establishmentType = establishmentType;
        this.establishmentName = establishmentName;
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


    // to create jsonObject from model
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("neighbourhood", neighbourhood)
                .add("establishmentType", establishmentType)
                .add("establishmentName", establishmentName)
                .build();
    }

    // to create Place from payload(jsonStr)
    // class level method, use static, call method using Place class
    public static Place create(String jsonString) {
		Reader reader = new StringReader(jsonString);
		JsonReader jr = Json.createReader(reader);
		JsonObject jo = jr.readObject();

        String neighbourhood = jo.getString("neighbourhood");
        String establishmentType = jo.getString("establishmentType");
        String establishmentName = jo.getString("establishmentName");

		Place place = new Place(neighbourhood, establishmentType, establishmentName);

		return place;
	}

}