package ru.iliya132.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.iliya132.repository.IMonitorRepository;
import ru.iliya132.service.MonitorService;

@Configuration
public class ServiceConfig {
    @Autowired
    IMonitorRepository monitorRepository;

    @Bean
    public MonitorService monitorService() {
        return new MonitorService(monitorRepository);
    }
}
