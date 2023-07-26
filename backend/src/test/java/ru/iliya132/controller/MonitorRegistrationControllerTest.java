package ru.iliya132.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.iliya132.BaseMvcTest;
import ru.iliya132.MonitoringBackendApp;
import ru.iliya132.config.TestDbConfig;
import ru.iliya132.model.User;
import ru.iliya132.service.security.UserDetailsServiceImpl;

import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(classes = {TestDbConfig.class, MonitoringBackendApp.class})
public class MonitorRegistrationControllerTest extends BaseMvcTest {
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private MockMvc mockMvc;
    private final Resource validMonitorResource = new ClassPathResource("monitor/valid-monitor.json");
    private final Resource invalidMonitorResource = new ClassPathResource("monitor/invalid-monitor.json");

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();

        User user = new User("default@default.ru", new BCryptPasswordEncoder().encode("default"));
        userDetailsService.saveUser(user);
    }

    @Test
    @WithMockUser(username = "default@default.ru", password = "default")
    public void itCanRegisterValidMonitor() {
        act(() -> mockMvc.perform(MockMvcRequestBuilders.post("/api/monitor/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validMonitorResource.getContentAsString(StandardCharsets.UTF_8)))
                .andExpect(status().is2xxSuccessful())
        );
    }

    @Test
    @WithMockUser(username = "default")
    public void itShouldThrowInvalidMonitor() {
        act(() -> mockMvc.perform(MockMvcRequestBuilders.post("/api/monitor/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidMonitorResource.getContentAsString(StandardCharsets.UTF_8)))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest()));
    }

    @Test
    public void itShouldRedirectToLoginWhenUnauthorized() {
        act(() -> mockMvc.perform(MockMvcRequestBuilders.post("/api/monitor/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validMonitorResource.getContentAsString(StandardCharsets.UTF_8)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
        );
    }
}
