package ru.iliya132.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.iliya132.grpc.GrpcServer;

@Configuration
@Profile("!test")
public class GrpcServerConfig {
    @Autowired
    private ServiceConfig serviceConfig;

    @Value("${monitoring.grpc.port:3001}")
    private int port;

    @Bean(initMethod = "start", destroyMethod = "shutDown")
    public GrpcServer grpcServer() {
        return new GrpcServer(port, serviceConfig.monitoringGrpcService());
    }
}
