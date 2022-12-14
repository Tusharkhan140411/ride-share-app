package com.mlab.rideshare.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlab.rideshare.util.response.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthFailureHandler implements AuthenticationEntryPoint {

    private final ObjectMapper jacksonMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream()
                .write(jacksonMapper.writeValueAsBytes(
                        ResponseBuilder.buildResponse(
                                HttpStatus.UNAUTHORIZED, "Authentication Failed")));
    }
}
