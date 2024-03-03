package fabrico.nova.optics.service.chat;

import fabrico.nova.optics.model.ChatEntity;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public interface ChatService {

    Optional<ChatEntity> findChatById(Long chatId);

    Optional<ChatEntity> findChatByName(String chatNames);

    void saveChatInfo(Message message);
}
