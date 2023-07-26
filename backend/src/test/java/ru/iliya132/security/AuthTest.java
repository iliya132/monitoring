package ru.iliya132.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.EntityUtils;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(classes = {TestDbConfig.class, MonitoringBackendApp.class})
public class AuthTest extends BaseMvcTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private User defaultUser;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserDetailsServiceImpl userService;

    @BeforeEach
    public void setup() {
        var passEncoder = new BCryptPasswordEncoder();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
        defaultUser = new User("default@default.ru", "default");
        userService.saveUser(new User(defaultUser.getUsername(), passEncoder.encode(defaultUser.getPassword())));
    }

    @Test
    public void cantGetIndexPageWithoutAuth() {
        act(() -> mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login")));
    }

    @Test
    @WithMockUser(username = "default@default.ru")
    public void canGetIndexPageWhenAuthorized() {
        act(() -> mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().is2xxSuccessful()));
    }

    @Test
    public void canGetLoginPage() {
        act(() -> mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().is2xxSuccessful()));
    }

    @Test
    public void canGetRegisterPage() {
        act(() -> mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(status().is2xxSuccessful()));
    }

    @Test
    public void canRegister() {
        var testUser = new User("test@default.ru", "test", "test");
        act(() -> mockMvc.perform(
                        MockMvcRequestBuilders.post("/register")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                        new BasicNameValuePair("username", testUser.getUsername()),
                                        new BasicNameValuePair("password", testUser.getPassword()),
                                        new BasicNameValuePair("confirmPassword", testUser.getConfirmPassword()))
                                )))
                )
                .andExpect(status().is2xxSuccessful()));
    }

    @Test
    public void canLogin() {
        act(() -> mockMvc.perform(
                        MockMvcRequestBuilders.post("/perform_login")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                                new BasicNameValuePair("username", defaultUser.getUsername()),
                                                new BasicNameValuePair("password", defaultUser.getPassword())
                                        )))
                                ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/")));
    }

    @Test
    public void cantLoginWithWrongCredentials() {
        act(() -> mockMvc.perform(
                        MockMvcRequestBuilders.post("/perform_login")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(EntityUtils.toString(new UrlEncodedFormEntity(List.of(
                                                new BasicNameValuePair("username", defaultUser.getUsername()),
                                                new BasicNameValuePair("password", "wrong_password")
                                        )))
                                ))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error=true")));
    }
}
