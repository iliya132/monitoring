package ru.iliya132;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MonitoringBackendApp {
    public static void main(String[] args) {
        SpringApplication.run(MonitoringBackendApp.class, args);
    }
}
