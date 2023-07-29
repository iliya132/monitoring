package ru.iliya132.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.iliya132.processors.PingProcessor;
import ru.iliya132.service.MetricService;

@Configuration
public class ProcessorsConfig {
    @Autowired
    private MetricService metricService;

    @Bean
    public PingProcessor pingProcessor() {
        return new PingProcessor(metricService);
    }
}
