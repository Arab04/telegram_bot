package fabrico.nova.optics.service.user;

import fabrico.nova.optics.model.UserEntity;
import fabrico.nova.optics.model.constants.UserRole;
import fabrico.nova.optics.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void saveUser(Message message) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        UserEntity entity = new UserEntity();
        entity.setUserName(message.getContact().getFirstName());
        entity.setChatId(message.getContact().getUserId());
        entity.setNumber(message.getContact().getPhoneNumber());
        entity.setRegisteredDate(currentDate.format(formatter));
        userRepository.save(entity);
    }

    @Override
    public void setUserAsAdmin(Message message) {
        Optional<UserEntity> user = userRepository.userWithChatId(message.getChatId());
        if (user.isPresent()) {
            UserEntity entity = user.get();
            entity.setRole(UserRole.ADMIN);
            userRepository.save(entity);
        }
    }

    @Override
    public Optional<UserEntity> findByChatId(Message message) {
        return userRepository.userWithChatId(message.getChatId());
    }
}
