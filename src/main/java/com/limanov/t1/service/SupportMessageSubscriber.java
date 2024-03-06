package main.java.com.limanov.t1.service;

import main.brocker.Subscription;
import main.java.com.limanov.t1.model.MessageDto;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Component
public class SupportMessageSubscriber {
    private final SupportService supportService;

    public SupportMessageSubscriber(SupportService supportService) {
        this.supportService = supportService;
    }

    @Subscription
    public void subscriber(MessageDto messageDto) {
        supportService.addNewMessage(messageDto);
        System.out.printf("Input message: %s%n", messageDto.getContentMessage());
    }
}
