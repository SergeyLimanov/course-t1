package com.limanov.t1.repository;

import com.limanov.t1.ApplicationContext;
import com.limanov.t1.annotation.Inject;
import com.limanov.t1.annotation.RequestMapping;
import com.limanov.t1.annotation.RestController;
import com.limanov.t1.servlet.SupportServlet;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServletRepositoryImpl  implements ServletRepository{
    private ApplicationContext applicationContext;
    private final Map<String, SupportServlet> servlets = new HashMap<>();
    private final Map<String, Map<String, Method>> requestMappings  = new HashMap<>();

    @Override
    public void initServletRepository() {
        applicationContext.getInstances().values().stream()
                .filter(bean -> bean.getClass().isAnnotationPresent(RestController.class))
                .forEach(bean -> {
                            String classPath = bean.getClass().getAnnotation(RequestMapping.class).path();
                            addNewServlet(classPath, (SupportServlet) bean);
                            Arrays.stream(bean.getClass().getDeclaredMethods())
                                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                                    .forEach(method -> {
                                        String type = method.getAnnotation(RequestMapping.class).methodType();
                                        String path = method.getAnnotation(RequestMapping.class).path();
                                        if (Objects.nonNull(path)) {
                                            path = method.getDeclaringClass().getAnnotation(RequestMapping.class).path() + path;
                                            addNewServlet(path, (SupportServlet) bean);
                                        }
                                        addNewMethod(path, type, method);
                                    });
                        }
                );
    }

    @Inject
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private void addNewServlet(String path, SupportServlet controller) {
        servlets.put(path, controller);
    }

    @Override
    public SupportServlet getServlet(String path) {
        return servlets.get(path);
    }

    private Map<String, Method> getMethodsMap(String path) {
        if (Objects.isNull(requestMappings.get(path))) {
            return new HashMap<>();
        }
        return requestMappings.get(path);
    }

    private void addNewMethod(String path, String type, Method method) {
        Map<String, Method> methodsMap = getMethodsMap(path);
        methodsMap.put(type, method);
        requestMappings.put(path, methodsMap);
    }

    @Override
    public Map<String, Method> getMethod(String path) {
        return requestMappings.get(path);
    }
}
