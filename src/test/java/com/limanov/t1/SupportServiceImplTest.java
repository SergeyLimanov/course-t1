package com.limanov.t1;

import com.limanov.t1.model.MessageDto;
import com.limanov.t1.repository.SupportRepositoryImpl;
import com.limanov.t1.service.SupportServiceImpl;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class SupportServiceImplTest {

    SupportRepositoryImpl supportRepository;
    SupportServiceImpl service;
    MessageDto message = new MessageDto("Test message");

    @Test
    void add_message_is_correct() {
        service = new SupportServiceImpl();
        supportRepository = mock(SupportRepositoryImpl.class);
        service.setContainer(supportRepository);
        service.addNewMessage(message);
        verify(supportRepository).addNewMessage(message);
        verify(supportRepository, times(1)).addNewMessage(message);

    }
}
