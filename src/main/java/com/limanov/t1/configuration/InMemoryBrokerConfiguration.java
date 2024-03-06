package main.java.com.limanov.t1.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.brocker.*;
import main.java.com.limanov.t1.model.MessageDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(BrokerConfig.class)
@Configuration
public class InMemoryBrokerConfiguration {

    @Bean
    public Publisher<MessageDto> supportMessagePublisher(ObjectMapper objectMapper, InMemoryBroker inMemoryBroker) {
        return new Publisher<>(objectMapper ,inMemoryBroker);
    }

}


