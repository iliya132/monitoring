import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iliya132.MonitoringBackendApp;
import ru.iliya132.config.AppConfig;

@SpringBootTest(classes = AppConfig.class)
public class BaseIntegrationalTest {
    @Test
    public void itCanStart() {
        System.out.println("it worked");
    }
}
