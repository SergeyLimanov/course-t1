package com.limanov.t1.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.limanov.t1.annotation.Configuration;
import com.limanov.t1.annotation.Instance;
import com.limanov.t1.repository.ServletRepositoryImpl;
import com.limanov.t1.repository.SupportRepositoryImpl;
import com.limanov.t1.service.SupportServiceImpl;
import com.limanov.t1.service.SupportService;
import com.limanov.t1.servlet.DispatcherServlet;
import com.limanov.t1.servlet.SupportServlet;
import com.limanov.t1.servlet.SupportServletImpl;
import jakarta.servlet.http.HttpServlet;

@Configuration
public class SupportConfiguration {

    @Instance
    public SupportServlet supportServlet() {
        return new SupportServletImpl();
    }
    @Instance
    public ServletRepositoryImpl controllersContainer() {
        return new ServletRepositoryImpl();
    }
    @Instance
    public SupportService supportService() {
        return new SupportServiceImpl();
    }
    @Instance
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    @Instance
    public HttpServlet myDispatcherServlet() {
        return new DispatcherServlet();
    }
    @Instance
    public SupportRepositoryImpl supportRepository() {
        return new SupportRepositoryImpl();
    }

}
