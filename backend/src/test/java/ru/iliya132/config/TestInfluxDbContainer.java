package ru.iliya132.config;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.testcontainers.containers.InfluxDBContainer;
import org.testcontainers.utility.DockerImageName;

public class TestInfluxDbContainer {
    private TestInfluxDbContainer() {
    }

    private static final String IMAGE_VERSION = "influxdb:1.8.10";
    private static final InfluxDBContainer<?> influxDBContainer = new InfluxDBContainer<>(DockerImageName.parse(IMAGE_VERSION))
            .withDatabase("monitoring")
            .withAdmin("admin")
            .withAdminPassword("root");

    public static InfluxDB getInstance() {
        if (!influxDBContainer.isRunning()) {
            influxDBContainer.start();
        }
        return InfluxDBFactory.connect(
                influxDBContainer.getUrl(),
                "admin",
                "root"
        );
    }
}
