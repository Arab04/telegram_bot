package fabrico.nova.optics.service.crone;

import fabrico.nova.optics.model.ChatEntity;
import fabrico.nova.optics.model.CustomerEntity;
import fabrico.nova.optics.model.UserEntity;
import fabrico.nova.optics.repository.ChatRepository;
import fabrico.nova.optics.repository.CustomerRepository;
import fabrico.nova.optics.repository.UserRepository;
import fabrico.nova.optics.service.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CroneService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final TelegramBotService botService;

    @Scheduled(fixedDelay = 10000)
    private void triggerCustomer() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<CustomerEntity> customers = customerRepository.listOfCustomers(currentDate.format(formatter));
        Optional<ChatEntity> lenses = chatRepository.findChatByName("Lenses");
        SendMessage sendMessage = new SendMessage();
        for (CustomerEntity customer : customers) {
            Optional<UserEntity> user = userRepository.findUserByNumber(customer.getCustomerNumber());
            if (!customer.getDeleted()) {
                if (user.isPresent()) {
                    UserEntity userEntity = user.get();
                    sendMessage.setText(String.format("Предупреждение от Nova Optics: Уважаемый покупатель, вы купили 6 месяцев назад(%s), линзы с именем %s. Они непригодны для использования. Будьте осторожны, это может нанести вред вашим глазам. Мы советуем вам заменить их на новые. С уважением Нова Оптикс.", customer.getRegisteredDate(), customer.getLinza()));
                    sendMessage.setChatId(String.valueOf(userEntity.getChatId()));
                    customer.setDeleted(true);
                    customerRepository.save(customer);
                    botService.executeMessage(sendMessage);
                    if (lenses.isPresent()) {
                        sendMessage.setText(String.format("Покупателю отправлено предупреждение о покупке новых линз: %s, название линз: %s", customer.getCustomerNumber(), customer.getLinza()));
                        sendMessage.setChatId(String.valueOf(lenses.get().getGroupId()));
                        botService.executeMessage(sendMessage);
                    }
                } else {
                    if (lenses.isPresent()) {
                        sendMessage.setText(String.format("Пожалуйста, сообщите клиенту номер телефона: %s, чтобы купить новые линзы,\n линзы были куплены в: %s, \n название линз: %s", customer.getCustomerNumber(), customer.getRegisteredDate(), customer.getLinza()));
                        sendMessage.setChatId(String.valueOf(lenses.get().getGroupId()));
                        botService.executeMessage(sendMessage);
                        customer.setDeleted(true);
                        customerRepository.save(customer);
                    }
                }
            }
        }
    }
}
