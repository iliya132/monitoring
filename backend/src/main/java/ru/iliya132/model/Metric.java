package ru.iliya132.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Measurement(name = "metric")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Metric {
    @Column(name = "system")
    private String system;
    @Column(name = "owner")
    private String owner;
    @Column(name = "numeric_value")
    private Double numericValue;
    @Column(name = "str_value")
    private String strValue;
    @Column(name = "time")
    private Instant time;
    @Builder.Default
    private Map<String, String> tags = new HashMap<>();
}
