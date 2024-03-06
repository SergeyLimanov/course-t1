package main.java.com.limanov.t1.controller;

import main.brocker.Publisher;
import main.java.com.limanov.t1.model.MessageDto;
import main.java.com.limanov.t1.service.SupportService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/help-service/v1")
@RestController
public class SupportController {
    private final SupportService supportService;
    private final Publisher<MessageDto> publisher;

    public SupportController(SupportService supportService, Publisher<MessageDto> publisher) {
        this.supportService = supportService;
        this.publisher = publisher;
    }

    @GetMapping("/support")
    public MessageDto getMainMessage() {
        return supportService.getRandomMessage();
    }

    @PostMapping("/support")
    public String createOrUpdateDog(@RequestBody MessageDto messageDto) {
        publisher.publish(messageDto);
        return String.format("You have been added new message: %s", messageDto.getContentMessage());
    }
}
