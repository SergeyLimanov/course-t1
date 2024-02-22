package main.java.com.limanov.t1.service;

import lombok.RequiredArgsConstructor;
import main.java.com.limanov.t1.annotation.Logged;
import main.java.com.limanov.t1.model.MessageDto;
import main.java.com.limanov.t1.repository.SupportRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
@Logged
public class SupportServiceImpl implements SupportService {
    private final SupportRepositoryImpl container;

    public SupportServiceImpl(SupportRepositoryImpl container) {
        this.container = container;
    }

    @Override
    public Boolean addNewMessage(MessageDto message) {
       return container.addNewMessage(message);
    }

    @Override
    public MessageDto getRandomMessage() {
        int min = 0;
        int max = container.getSize() - 1;
        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;
        return container.getMessageById(randomNumber);
    }
}
