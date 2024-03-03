package fabrico.nova.optics.service.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

public interface BotMessageSender {

    SendMessage sendBotMessage(Long chatId, String text);

    SendMessage sendMenu(Long chatId, String text, ReplyKeyboardMarkup keyboard);

    List<SendMessage> sendPost(String text);

    SendPhoto sendPhoto(Message message, String path);
}
