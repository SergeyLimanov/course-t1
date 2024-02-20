package com.limanov.t1;

import com.limanov.t1.annotation.Logged;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler implements InvocationHandler {
    private final Object object;

    public LoggingInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Logged.class)) {
            System.out.print("Before rarget method");
            Object result = method.invoke(object, args);
            System.out.print("After target method");
            return result;
        } else {
            return method.invoke(object, args);
        }
    }
}
