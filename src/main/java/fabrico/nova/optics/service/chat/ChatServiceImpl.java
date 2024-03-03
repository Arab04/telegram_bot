package fabrico.nova.optics.service.chat;

import fabrico.nova.optics.model.ChatEntity;
import fabrico.nova.optics.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Optional<ChatEntity> findChatById(Long chatId) {
        return chatRepository.findChatById(chatId);
    }

    @Override
    public Optional<ChatEntity> findChatByName(String chatName) {
        return chatRepository.findChatByName(chatName);
    }

    @Override
    public void saveChatInfo(Message message) {
        ChatEntity chat = new ChatEntity();
        chat.setGroupId(message.getChat().getId());
        chat.setChatNames(message.getChat().getTitle());
        chatRepository.save(chat);
    }
}
