package vttp2022.miniproject.anythingapp.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.miniproject.anythingapp.models.Place;

@Repository
public class AnythingappRepository {

    @Autowired 
    @Qualifier("redislab")
	private RedisTemplate<String, String> template;
    
    public void saveToRedis(Place place, String userName) {

        // get username from input to store in redis as keys
        String jsonObjectName = userName;

        // declare the redis list
        ListOperations<String, String> listOps = template.opsForList();
		
        // adding by the back 
		listOps.leftPush(jsonObjectName, place.toJson().toString());
        
    }

    public Optional<List<Place>> getFromRedis(String userName) {

        // if redis does not contain the username, return empty container
        if (!template.hasKey(userName)) {
            return Optional.empty();
        }
        
        List<Place> places = new LinkedList<>();
		ListOperations<String, String> listOps = template.opsForList();
		long size = listOps.size(userName);

        // long topThree = 3;

        // if (size < 3) {
        //     topThree = size;
        // }
        
		for (long i = 0; i < size; i++) {
			String payloadStr = listOps.index(userName, i);
			places.add(Place.create(payloadStr));
		}

		return Optional.of(places);

    }

    public void updateToRedis(List<Place> places, String userName) {

        // get username from input to store in redis as keys
        String jsonObjectName = userName;

        // delete key and its corresponding data
        template.delete(userName);

        // declare the redis list
        ListOperations<String, String> listOps = template.opsForList();
		
        // adding by the back 
        for (int i = 0; i < places.size(); i++) {
            listOps.leftPush(jsonObjectName, places.get(i).toJson().toString());
        }
		
        
    }


}
