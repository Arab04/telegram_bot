package fabrico.nova.optics.service.message;

import fabrico.nova.optics.model.CustomerEntity;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

public interface MessageSender {

    void sendMessagesToCustomers(List<CustomerEntity> customers);

    SendMessage sendBotMessage(Long chatId, String text);

    SendMessage sendMenu(Long chatId, String text, ReplyKeyboardMarkup keyboard);

    List<SendMessage> sendPost(String text);
}
