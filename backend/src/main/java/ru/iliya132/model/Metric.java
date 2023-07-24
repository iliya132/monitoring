package ru.iliya132.model;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Measurement(name = "metric")
public class Metric {
    @Column(name = "system")
    private String system;
    @Column(name = "owner")
    private String owner;
    @Column(name = "numeric_value")
    private Long numericValue;
    @Column(name = "str_value")
    private String strValue;
    @Column(name = "time")
    private Instant time;
    private Map<String, String> tags = new HashMap<>();

    public String getSystem() {
        return system;
    }

    public Metric setSystem(String system) {
        this.system = system;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public Metric setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public Long getNumericValue() {
        return numericValue;
    }

    public Metric setNumericValue(Long numericValue) {
        this.numericValue = numericValue;
        return this;
    }

    public String getStrValue() {
        return strValue;
    }

    public Metric setStrValue(String strValue) {
        this.strValue = strValue;
        return this;
    }

    public Metric addTag(String tagName, String tagValue) {
        tags.put(tagName, tagValue);
        return this;
    }

    public Map<String, String> getTags() {
        return this.tags;
    }

    public Instant getTime() {
        return this.time;
    }

    public Metric setTime(Instant time) {
        this.time = time;
        return this;
    }
}
