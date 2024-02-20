package com.limanov.t1.repository;

import com.limanov.t1.model.MessageDto;

import java.util.List;

public interface SupportRepository {

    List<MessageDto> getHelpList();

    Boolean addNewMessage(MessageDto message);

    MessageDto getMessageById(Integer id);

    Integer getSize();
}
