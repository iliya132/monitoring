package ru.iliya132.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iliya132.model.Monitor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonitorDto {
    Long id;
    String cron;
    String url;
    String type;
    String service;
    String description;
    LocalDateTime nextRun;

    public static MonitorDto fromMonitor(Monitor monitor) {
        return MonitorDto.builder()
                .id(monitor.getId())
                .cron(monitor.getCron())
                .url(monitor.getUrl())
                .type(monitor.getMonitorType().name())
                .service(monitor.getService())
                .description(monitor.getDescription())
                .nextRun(LocalDateTime.ofInstant(monitor.getNextRun(), ZoneOffset.UTC))
                .build();
    }
}
