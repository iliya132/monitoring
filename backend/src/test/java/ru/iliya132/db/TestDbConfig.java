package ru.iliya132.db;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.iliya132.BaseDbTest;

import java.util.List;

public class TestDbConfig extends BaseDbTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void itCanStart() {
    }

    @Test
    public void itShouldExecuteLiquibaseChangesets() {
        var sql = "SELECT tablename FROM pg_catalog.pg_tables where schemaname = 'public';";
        var tableNames = jdbcTemplate.queryForList(sql, String.class);
        Assertions.assertThat(tableNames).hasSizeGreaterThan(0);
        Assertions.assertThat(tableNames).containsAll(List.of(
                "databasechangeloglock",
                "databasechangelog",
                "users",
                "monitors"
        ));
    }
}
