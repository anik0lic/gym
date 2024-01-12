package raf.gymreservationservice.configuration;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BulkheadConfiguration {
    @Bean
    public Bulkhead movieServiceBulkhead() {
        BulkheadConfig bulkheadConfig = BulkheadConfig.custom()
                .maxWaitDuration(Duration.ofMillis(500))
                .maxConcurrentCalls(5)
                .build();

        BulkheadRegistry bulkheadRegistry = BulkheadRegistry.of(bulkheadConfig);

        return bulkheadRegistry.bulkhead("reservationServiceBulkhead");
    }
}
