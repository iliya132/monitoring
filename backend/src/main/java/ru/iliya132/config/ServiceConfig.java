package ru.iliya132.config;

import org.influxdb.InfluxDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.iliya132.grpc.MonitoringGrpcServiceImpl;
import ru.iliya132.repository.IMonitorRepository;
import ru.iliya132.service.MetricService;
import ru.iliya132.service.MonitorService;

@Configuration
public class ServiceConfig {
    @Autowired
    IMonitorRepository monitorRepository;

    @Autowired
    InfluxDB influxDB;

    @Value("${spring.influx.database}")
    String influxDatabase;

    @Bean
    public MonitorService monitorService() {
        return new MonitorService(monitorRepository);
    }

    @Bean
    public MetricService metricService() {
        return new MetricService(influxDB, influxDatabase);
    }

    @Bean
    public MonitoringGrpcServiceImpl monitoringGrpcService() {
        return new MonitoringGrpcServiceImpl(metricService());
    }
}
