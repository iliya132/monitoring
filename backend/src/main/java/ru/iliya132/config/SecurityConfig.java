package ru.iliya132.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import ru.iliya132.service.security.AuthEntryPoint;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("#{'${monitoring.security.allowed-origins}'.split(',')}")
    List<String> allowedOrigins;

    @Bean
    public AuthEntryPoint authEntryPoint() {
        return new AuthEntryPoint();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .cors().configurationSource(src -> {
                    var config = new CorsConfiguration();
                    config.setAllowedOrigins(allowedOrigins);
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowedMethods(List.of("*"));
                    return config;
                }).and()
                .authorizeHttpRequests()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/perform_login").permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**.css")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**.html")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/**.js")).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutUrl("/perform_logout")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint());
        return httpSecurity.build();
    }
}
