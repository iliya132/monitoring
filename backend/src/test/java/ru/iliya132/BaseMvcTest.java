package ru.iliya132;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@WebAppConfiguration
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseMvcTest extends BaseDbTest {
    protected void act(Callback callBack) {
        try {
            callBack.act();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FunctionalInterface
    protected interface Callback {
        void act() throws Exception;
    }
}
