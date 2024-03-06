package main.brocker;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SubscriberBeanPostProcessor implements BeanPostProcessor {

    protected final static Map<Method, Object> beanByMethodMap = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Subscription.class))
                .forEach(method -> beanByMethodMap.put(method, bean));
        return bean;
    }
}
