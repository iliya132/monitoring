package ru.iliya132.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import ru.iliya132.executors.MonitoringExecutor;

@Configuration
@Profile("!test")
public class TmsConfig {
    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private ProcessorsConfig processorsConfig;

    @Bean
    public MonitoringExecutor monitoringExecutor() {
        return new MonitoringExecutor(serviceConfig.monitorService())
                .registerProcessor(processorsConfig.pingProcessor())
                .registerProcessor(processorsConfig.responseTimeProcessor())
                .registerProcessor(processorsConfig.responseProcessor());
    }

    @Scheduled(fixedDelay = 10_000)
    public void runMonitoringExecutor() {
        monitoringExecutor().start();
    }
}
