package hu.poketerkep.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;

@Configuration
public class AppConfig {
    private final Environment env;

    @Autowired
    public AppConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                boolean isTestProfile = Arrays.asList(env.getActiveProfiles()).contains(LocalConstants.TEST_PROFILE);
                boolean isDevelopmentProfile = Arrays.asList(env.getActiveProfiles()).contains(LocalConstants.DEVELOPMENT_PROFILE);

                if (isTestProfile || isDevelopmentProfile) {
                    registry.addMapping("/**").allowedOrigins("*");
                } else {
                    registry.addMapping("/**").allowedOrigins("https://www.poketerkep.hu");
                }
            }
        };
    }
}
