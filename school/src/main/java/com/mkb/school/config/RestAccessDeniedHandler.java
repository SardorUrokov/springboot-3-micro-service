package com.mkb.school.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkb.school.response.ApiResponse;
import com.mkb.school.response.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {

        ApiResponse response = new ApiResponse(HttpStatus.FORBIDDEN,
                new ResponseObject(
                        "Access Denied", "Access Denied"
                )
        );

        httpServletResponse.setStatus(403);
        httpServletResponse.setContentType("application/json");
        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, response);
        out.flush();
    }
}