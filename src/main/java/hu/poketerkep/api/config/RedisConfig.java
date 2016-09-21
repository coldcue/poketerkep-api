package hu.poketerkep.api.config;

import hu.poketerkep.shared.datasource.PokemonDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Configuration
    @Profile("default")
    public class ProductionRedisConfig {
        @Value("${redis.server.host}")
        private String redisServerHost;

        @Bean
        JedisPool jedisPool() {
            JedisPoolConfig poolConfig = new JedisPoolConfig();

            //Set the total pools to handle many requests
            poolConfig.setMaxTotal(64);
            poolConfig.setMaxIdle(64);

            // This has to be set, otherwise there will be a 100% cpu problem
            poolConfig.setMinIdle(8);

            //Disable tests
            poolConfig.setTestOnCreate(false);
            poolConfig.setTestOnReturn(false);
            poolConfig.setTestOnBorrow(false);
            poolConfig.setTestWhileIdle(false);

            return new JedisPool(poolConfig, redisServerHost);
        }

        @Bean
        PokemonDataSource pokemonDataSource() {
            return new PokemonDataSource(jedisPool());
        }
    }

    @Configuration
    @Profile("development")
    public class DevelopmentRedisConfig {
        @Bean
        JedisPool jedisPool() {
            return new JedisPool(new JedisPoolConfig(), "localhost");
        }

        @Bean
        PokemonDataSource pokemonDataSource() {
            return new PokemonDataSource(jedisPool());
        }
    }
}
