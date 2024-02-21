package main.brocker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Publisher <T> {

    private final ObjectMapper objectMapper;
    private final  InMemoryBroker inMemoryBroker;

    public void publish(T object) {
        try {
            inMemoryBroker.publish(objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



}
