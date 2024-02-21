package main.java.com.limanov.t1.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.limanov.t1.model.MessageDto;

@Slf4j
@Component
public class SupportMessageSubscriber {

    public void subscriber(MessageDto messageDto) {
        log.info("Input message: {}", messageDto.getContentMessage());
    }
}
