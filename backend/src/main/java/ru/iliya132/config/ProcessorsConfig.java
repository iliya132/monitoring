package ru.iliya132.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.iliya132.processors.PingProcessor;
import ru.iliya132.processors.ResponseProcessor;
import ru.iliya132.processors.ResponseTimeProcessor;
import ru.iliya132.service.MetricService;

@Configuration
public class ProcessorsConfig {
    @Autowired
    private MetricService metricService;

    @Bean
    public PingProcessor pingProcessor() {
        return new PingProcessor(metricService);
    }

    @Bean
    ResponseTimeProcessor responseTimeProcessor() {
        return new ResponseTimeProcessor(metricService);
    }

    @Bean
    ResponseProcessor responseProcessor() {
        return new ResponseProcessor(metricService);
    }
}
