package hu.poketerkep.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.logging.Logger;


@RestController
public class HealthController {
    private final JedisPool jedisPool;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    public HealthController(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @RequestMapping("/health")
    ResponseEntity<String> health() throws Exception {

        String jedisMsg = "JEDIS - " +
                " active: " + jedisPool.getNumActive() +
                " idle: " + jedisPool.getNumIdle() +
                " waiters: " + jedisPool.getNumWaiters() +
                " mean borrow time: " + jedisPool.getMeanBorrowWaitTimeMillis();

        log.info(jedisMsg);

        return ResponseEntity.ok("OK");
    }
}
