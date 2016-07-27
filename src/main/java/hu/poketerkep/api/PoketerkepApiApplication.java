package hu.poketerkep.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class PoketerkepApiApplication extends SpringBootServletInitializer {

    private static Log logger = LogFactory.getLog(PoketerkepApiApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PoketerkepApiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PoketerkepApiApplication.class);
    }
}
