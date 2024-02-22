package main.brocker;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static main.brocker.SubscriberBeanPostProcessor.beanByMethodMap;

public class SubscriberContainer {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private final ObjectMapper objectMapper;
    private final InMemoryBroker inMemoryBroker;

    public SubscriberContainer(ObjectMapper objectMapper, InMemoryBroker inMemoryBroker) {
        this.objectMapper = objectMapper;
        this.inMemoryBroker = inMemoryBroker;
    }

@PostConstruct
    private void init() {
        beanByMethodMap.forEach((key, value) -> executorService.submit(() -> {
            while (true) {
                String message = inMemoryBroker.receiveMessage();
                key.invoke(
                        value,
                        objectMapper.readValue(message, key.getParameterTypes()[0])
                );
            }
        }));
    }
}
