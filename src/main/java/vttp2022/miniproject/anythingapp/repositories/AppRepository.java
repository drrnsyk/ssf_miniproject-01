package vttp2022.miniproject.anythingapp.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.miniproject.anythingapp.models.App;

@Repository
public class AppRepository {

    @Autowired 
    @Qualifier("redislab")
	private RedisTemplate<String, String> template;
    
    public void saveToRedis(App entry) {
        String jsonObjectName = entry.getNeighbourhood();

        List<App> entries = new LinkedList<>();
        entries.add(entry);

        ListOperations<String, String> listOps = template.opsForList();
        long l = listOps.size(jsonObjectName);

		if (l > 0)
			listOps.trim(jsonObjectName, 0, l);

		listOps.leftPushAll(jsonObjectName, entries.stream().map(v -> v.toJson().toString()).toList());
    }


}
