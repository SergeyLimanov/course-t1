package com.limanov.t1.controller;

import com.limanov.t1.model.MessageDto;
import com.limanov.t1.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/help-service/v1")
@RestController
public class SupportController {
    private final SupportService supportService;

    @GetMapping("/support")
    public MessageDto getMainMessage() {
        return supportService.getRandomMessage();
    }

    @PostMapping("/support")
    public String createOrUpdateDog(@RequestBody String messageDto) {
        supportService.addNewMessage(new MessageDto(messageDto));
        return String.format("You have been added new message: %s", messageDto);
    }
}
