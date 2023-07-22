package ru.iliya132.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@TestConfiguration
@Profile("test")
public class TestDbConfig {
    private Logger log = LoggerFactory.getLogger(TestDbConfig.class);

    @Bean
    DataSourceProperties dataSourceProperties() {
        var postgreSQLContainer = TestPostgresqlContainer.getInstance();
        var ds = new DataSourceProperties();
        ds.setUrl(postgreSQLContainer.getJdbcUrl());
        ds.setUsername(postgreSQLContainer.getUsername());
        ds.setPassword(postgreSQLContainer.getPassword());
        ds.setDriverClassName(postgreSQLContainer.getDriverClassName());
        log.info("configured dataSource with params: {} {} {} {}", postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword(),
                postgreSQLContainer.getDriverClassName());
        return ds;
    }

    @Bean
    DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
