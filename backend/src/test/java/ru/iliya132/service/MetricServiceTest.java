package ru.iliya132.service;

import org.assertj.core.api.Assertions;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.impl.InfluxDBResultMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.iliya132.BaseDbTest;
import ru.iliya132.model.Metric;

import java.time.Instant;
import java.util.HashMap;

public class MetricServiceTest extends BaseDbTest {
    @Autowired
    private InfluxDB influxDB;
    @Autowired
    private MetricService metricService;
    InfluxDBResultMapper dbResultMapper = new InfluxDBResultMapper();

    @BeforeEach
    public void startUp() {
        Query createDbQuery = new Query("create database test_db;");
        influxDB.query(createDbQuery);
        influxDB.setDatabase("test_db");
    }

    @Test
    public void itCanWriteSingleMetric() {
        // prepare
        var tags = new HashMap<String, String>();
        tags.put("metric_type", "ping");
        Metric metric = Metric.builder()
                .owner("test_user")
                .system("test_system")
                .strValue("200")
                .tags(tags)
                .build();
        Instant start = Instant.now();

        // act
        metricService.write(metric);
        metricService.flush();

        // assert
        Query query = new Query("select * from metric");
        var result = influxDB.query(query);
        var pojoResult = dbResultMapper.toPOJO(result, Metric.class);
        Assertions.assertThat(pojoResult).hasSize(1);
        var pojo = pojoResult.get(0);
        Assertions.assertThat(pojo.getOwner()).isEqualTo("test_user");
        Assertions.assertThat(pojo.getSystem()).isEqualTo("test_system");
        Assertions.assertThat(pojo.getStrValue()).isEqualTo("200");
        Assertions.assertThat(pojo.getTime().isAfter(start)).isTrue();
    }

}
