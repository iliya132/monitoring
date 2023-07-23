package ru.iliya132.service.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class AuthEntryPoint implements AuthenticationEntryPoint {
    Logger log = LoggerFactory.getLogger(AuthEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if(authException != null) {
            log.error("{}, uri:{}, url:{}, context-path:{}, query:{}",
                    authException.getMessage(),
                    request.getRequestURI(),
                    request.getRequestURL(),
                    request.getContextPath(),
                    request.getQueryString());
            response.sendRedirect("/login");
        }
    }
}
