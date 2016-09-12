package hu.poketerkep.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AppConfig {

    /**
     * This Executor Service provides threads for Async tasks
     *
     * @return
     */
    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(LocalConstants.MAX_THREADS);
    }


}
