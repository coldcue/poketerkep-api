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
            return new JedisPool(new JedisPoolConfig(), redisServerHost);
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
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setBlockWhenExhausted(true);
            poolConfig.setMaxWaitMillis(LocalConstants.MAX_WAIT_FOR_REDIS);

            return new JedisPool(poolConfig, "localhost");
        }

        @Bean
        PokemonDataSource pokemonDataSource() {
            return new PokemonDataSource(jedisPool());
        }
    }
}
