package com.limanov.t1.service;

import com.limanov.t1.annotation.Logged;
import com.limanov.t1.model.MessageDto;

public interface SupportService {

    @Logged
    Boolean addNewMessage(MessageDto message);

    @Logged
    MessageDto getRandomMessage();
}
