package ru.iliya132;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.iliya132.config.TestDbConfig;

// Requires docker to run
@SpringBootTest(classes = {TestDbConfig.class, MonitoringBackendApp.class})
@ActiveProfiles("test")
public class BaseDbTest {
}
