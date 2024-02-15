package com.limanov.t1.servlet;

import com.limanov.t1.model.MessageDto;

public interface SupportServlet {

    Boolean addNewMessage(MessageDto message);

    MessageDto getRandomMessage();
}
