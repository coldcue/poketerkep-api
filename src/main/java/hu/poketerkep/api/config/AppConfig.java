package hu.poketerkep.api.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
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

    private static final String DEVELOPMENT_PROFILE = "DEVELOPMENT_PROFILE";
    @Autowired
    Environment env;

    /**
     * Default AWS Credentials
     * http://docs.aws.amazon.com/java-sdk/latest/developer-guide/credentials.html
     *
     * @return AWS Credentials
     */
    @Bean
    AWSCredentialsProvider awsCredentialsProvider() {
        return new DefaultAWSCredentialsProviderChain();
    }

    @Bean
    AmazonDynamoDBAsync amazonDynamoDBAsync() {
        return AmazonDynamoDBAsyncClientBuilder.standard()
                .withCredentials(awsCredentialsProvider())
                .withRegion(Regions.EU_WEST_1).build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                if (Arrays.asList(env.getActiveProfiles()).contains(DEVELOPMENT_PROFILE)) {
                    registry.addMapping("/**").allowedOrigins("*");
                } else {
                    registry.addMapping("/**").allowedOrigins("https://www.poketerkep.hu");
                }
            }
        };
    }
}
