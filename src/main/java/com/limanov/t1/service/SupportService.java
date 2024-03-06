package main.java.com.limanov.t1.service;

import main.java.com.limanov.t1.annotation.Logged;
import main.java.com.limanov.t1.model.MessageDto;

public interface SupportService {

    @Logged
    Boolean addNewMessage(MessageDto message);

    @Logged
    MessageDto getRandomMessage();
}
