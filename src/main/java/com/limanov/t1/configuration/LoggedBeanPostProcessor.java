package main.java.com.limanov.t1.configuration;

import main.java.com.limanov.t1.annotation.Logged;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoggedBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Object> beanByNameMap = new HashMap<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
       if (bean.getClass().isAnnotationPresent(Logged.class)) {
           beanByNameMap.put(beanName, bean);
       }
       return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
