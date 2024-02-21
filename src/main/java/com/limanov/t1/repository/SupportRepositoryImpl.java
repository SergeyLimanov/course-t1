package com.limanov.t1.repository;

import com.limanov.t1.model.MessageDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SupportRepositoryImpl implements SupportRepository{
   private static final List<MessageDto> helpList = new ArrayList<>();

   static {
       helpList.add(new MessageDto("First Message"));
   }

   @Override
    public List<MessageDto> getHelpList() {
        return helpList;
    }

    @Override
    public Boolean addNewMessage(MessageDto message) {
       return helpList.contains(message) ? Boolean.FALSE : addMessageWithMark(message);
    }

    private boolean addMessageWithMark(MessageDto message) {
        helpList.add(message);
        return Boolean.TRUE;
    }

    @Override
    public MessageDto getMessageById(Integer id){
        return helpList.get(id);
    }

    @Override
    public Integer getSize() {
        return helpList.size();
    }
}
