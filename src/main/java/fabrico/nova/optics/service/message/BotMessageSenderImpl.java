package fabrico.nova.optics.service.message;

import fabrico.nova.optics.model.CustomerEntity;
import fabrico.nova.optics.model.UserEntity;
import fabrico.nova.optics.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

    private final UserRepository userRepository;

    @Override
    public void sendMessagesToCustomers(List<CustomerEntity> customers) {
        for (CustomerEntity customer : customers) {
            System.out.println(String.format("MESSAGE SENT TO CUSTOMER: %s NUMBER: %s", customer.getCustomerName(), customer.getCustomerNumber()));
        }
    }

    @Override
    public SendMessage sendBotMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        return message;
    }

    @Override
    public SendMessage sendMenu(Long chatId, String text, ReplyKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setReplyMarkup(keyboard);
        return message;
    }

    @Override
    public List<SendMessage> sendPost(String text) {
        List<UserEntity> users = userRepository.findAll();
        List<SendMessage> sendMessages = new ArrayList<>();
        for(UserEntity user: users) {
            sendMessages.add(sendBotMessage(user.getChatId(), text));
        }
        return sendMessages;
    }
}
