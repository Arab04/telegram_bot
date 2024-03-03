package fabrico.nova.optics.service.user;

import fabrico.nova.optics.model.UserEntity;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public interface UserService {

    void saveUser(Message message);

    void setUserAsAdmin(Message message);

    Optional<UserEntity> findByChatId(Message message);
}
