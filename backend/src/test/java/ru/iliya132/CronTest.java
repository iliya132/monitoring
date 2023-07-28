package ru.iliya132;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.support.CronExpression;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CronTest {
    @Test
    public void testCron() {
        CronExpression cronExpression = CronExpression.parse("0 */1 * * * *");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now:");
        System.out.println(now);
        System.out.println("from cron:");
        var cron = cronExpression.next(now);
        System.out.println(cron);
        System.out.println("now parsed:");
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        System.out.println("cron parsed:");
        System.out.println(cron.toInstant(ZoneOffset.UTC));

    }
}
