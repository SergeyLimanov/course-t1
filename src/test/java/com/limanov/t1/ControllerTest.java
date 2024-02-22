package test.java.com.limanov.t1;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.limanov.t1.SupportApplication;
import main.java.com.limanov.t1.model.MessageDto;
import main.java.com.limanov.t1.repository.SupportRepository;
import main.java.com.limanov.t1.service.SupportMessageSubscriber;
import main.java.com.limanov.t1.service.SupportService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SupportApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    SupportMessageSubscriber supportMessageSubscriber;
    @Autowired
    SupportService supportService;
    @Autowired
    SupportRepository supportRepository;

    @Test
    public void get_message_is_correct() throws Exception {
        mockMvc.perform(get("/help-service/v1/support")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("contentMessage").value("First Message"));
    }

    @Test
    public void other_methods_invokes() throws Exception {
        MessageDto messageDto = new MessageDto("Second message");
        mockMvc.perform(MockMvcRequestBuilders.post("/help-service/v1/support")
                        .content(objectMapper.writeValueAsBytes(messageDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(supportMessageSubscriber, times(1)).subscriber(eq(messageDto));
        verify(supportService, times(1)).addNewMessage(messageDto);
        verify(supportRepository, times(1)).addNewMessage(messageDto);
    }

}
