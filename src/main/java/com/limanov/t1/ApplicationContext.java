package com.limanov.t1;

import com.limanov.t1.annotation.Configuration;
import com.limanov.t1.annotation.Inject;
import com.limanov.t1.annotation.Instance;
import com.limanov.t1.annotation.Logged;
import org.reflections.Reflections;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ApplicationContext {


    private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

    public ApplicationContext() {

        init();
    }

    private void init() {
        Reflections reflections = new Reflections("com.limanov.t1");
        List<?> allConfigs = getAllConfigurationsClass(reflections);
        allConfigs.stream()
                .map(x -> x.getClass().getDeclaredMethods())
                .flatMap(Arrays::stream)
                .filter(x -> Objects.nonNull(x.getAnnotation(Instance.class)))
                .forEach(this::addBean);

        for (Map.Entry<Class<?>, Object> item : instances.entrySet()) {
            injectBeanFields(item);
        }
    }

    public Map<Class<?>, Object> getInstances() {
        return instances;
    }

    public Object getBeanForClass(Class<?> clazz) {
        return instances.get(clazz);
    }


    private void addBean(Method method) {
        Object bean;
        Class clazz;
        Object configClass = getInstance(method.getDeclaringClass());
        try {
            clazz = method.getReturnType();
            bean = method.invoke(configClass);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        if (Objects.nonNull(bean)) {
            if (Arrays.stream(bean.getClass().getDeclaredMethods())
                    .anyMatch(y -> y.isAnnotationPresent(Logged.class))) {
                instances.put(clazz, wrapWithLoggingProxy(bean));
            } else {
                instances.put(clazz, bean);
            }
        }
    }

    private List<?> getAllConfigurationsClass(Reflections reflections) {
        return reflections.getTypesAnnotatedWith(Configuration.class).stream().map(type -> {
            try {
                Object instance = type.getDeclaredConstructor().newInstance();
                instances.put(type, instance);
                return instance;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }


    private void injectBeanFields(Map.Entry<Class<?>, Object> beanEntry) {
        Arrays.stream(beanEntry.getValue().getClass().getDeclaredMethods()).distinct()
                .filter(x -> Objects.nonNull(x.getAnnotation(Inject.class)))
                .forEach(x -> {
                    try {
                        if (x.getParameterTypes().length == 0) {
                            x.invoke(beanEntry.getValue());
                        } else {
                            x.invoke(beanEntry.getValue(), getInstance(x.getParameterTypes()[0]));
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }


    public <T> T getInstance(Class<T> clazz) {
        if (clazz.getName().equals("com.limanov.t1.ApplicationContext")) {
            return (T) this;
        } else {
            return (T) Optional.ofNullable(instances.get(clazz)).orElseThrow(() -> new NoSuchElementException("Not found"));
        }
    }

    private Object wrapWithLoggingProxy(Object object) {
        InvocationHandler handler = new LoggingInvocationHandler(object);
        instances.put(InvocationHandler.class, handler);
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                (InvocationHandler) instances.get(InvocationHandler.class));
    }
}
