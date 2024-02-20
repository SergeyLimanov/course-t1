package com.limanov.t1.repository;

import com.limanov.t1.servlet.SupportServlet;

import java.lang.reflect.Method;
import java.util.Map;

public interface ServletRepository {
    void initServletRepository();

    SupportServlet getServlet(String path);

    Map<String, Method> getMethod(String path);
}
