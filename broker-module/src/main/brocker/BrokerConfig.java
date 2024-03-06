package main.brocker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {
    @Bean
    public SubscriberContainer subscriberContainer(ObjectMapper objectMapper, InMemoryBroker inMemoryBroker) {
        return new SubscriberContainer(objectMapper ,inMemoryBroker);
    }

    @Bean
    public SubscriberBeanPostProcessor subscriberBeanPostProcessor() {
        return new SubscriberBeanPostProcessor();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public InMemoryBroker inMemoryBroker() {
        return new InMemoryBroker();
    }

}
