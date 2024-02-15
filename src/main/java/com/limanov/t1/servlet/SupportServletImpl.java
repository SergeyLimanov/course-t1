package com.limanov.t1.servlet;

import com.limanov.t1.annotation.Inject;
import com.limanov.t1.annotation.RequestMapping;
import com.limanov.t1.annotation.RestController;
import com.limanov.t1.model.MessageDto;
import com.limanov.t1.service.SupportService;

@RestController
@RequestMapping(path = "/v1/support")
public class SupportServletImpl implements SupportServlet {
    private SupportService supportService;

    @Inject
    public void setSupportService(SupportService supportService) {
        this.supportService = supportService;
    }

    @RequestMapping(path = "/", methodType = "GET")
    public MessageDto getRandomMessage() {
        return new MessageDto(supportService.getRandomMessage().getContentMessage());
    }

    @RequestMapping(path = "/", methodType = "POST")
    public Boolean addNewMessage(MessageDto message) {
       return supportService.addNewMessage(message);
    }

}
