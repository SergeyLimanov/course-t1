package com.limanov.t1;

import com.limanov.t1.model.MessageDto;
import com.limanov.t1.service.SupportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupportServletTest  {

    private final MessageDto message = new MessageDto("Test message");

    @Test
    void get_message_return_msg_dto_correct() {
        SupportServletImpl servlet = new SupportServletImpl();
        SupportServiceImpl service = mock(SupportServiceImpl.class);
        servlet.setSupportService(service);
        when(service.getRandomMessage()).thenReturn(message);
        MessageDto result = servlet.getRandomMessage();
        verify(service, times(1)).getRandomMessage();

        assertEquals(message.getContentMessage(), result.getContentMessage());

    }
}