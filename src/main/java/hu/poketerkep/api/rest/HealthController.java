package hu.poketerkep.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;


@RestController
public class HealthController {
    private final JedisPool jedisPool;

    @Autowired
    public HealthController(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @RequestMapping("/health")
    ResponseEntity<String> health() throws Exception {
        //Test jedisPool (it will throw an exception)
        //Jedis resource = jedisPool.getResource();

        return ResponseEntity.ok("OK");
    }
}
