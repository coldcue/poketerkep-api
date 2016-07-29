package hu.poketerkep.api.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    private static final String ALL_POKEMON_CACHE = "allPokemonCache";

    @Override
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(ALL_POKEMON_CACHE,
                        CacheBuilder.newBuilder()
                                .expireAfterWrite(15, TimeUnit.SECONDS)
                                .maximumSize(100)
                                .build().asMap(), false);
            }
        };
    }
}
