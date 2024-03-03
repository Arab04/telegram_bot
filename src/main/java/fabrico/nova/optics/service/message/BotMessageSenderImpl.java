package fabrico.nova.optics.service.message;

import fabrico.nova.optics.model.UserEntity;
import fabrico.nova.optics.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BotMessageSenderImpl implements BotMessageSender {

    private final UserRepository userRepository;

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
        for (UserEntity user : users) {
            sendMessages.add(sendBotMessage(user.getChatId(), text));
        }
        return sendMessages;
    }

    @Override
    public SendPhoto sendPhoto(Message message, String path) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(path));
        sendPhoto.setCaption(message.getCaption());
        sendPhoto.setChatId(String.valueOf(message.getChatId()));
        return sendPhoto;
    }
}
