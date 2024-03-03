package fabrico.nova.optics.service.bot;

import fabrico.nova.optics.config.BotConfig;
import fabrico.nova.optics.model.CustomerEntity;
import fabrico.nova.optics.model.constants.UserState;
import fabrico.nova.optics.service.chat.ChatService;
import fabrico.nova.optics.service.customer.CustomerService;
import fabrico.nova.optics.service.message.BotMessageSender;
import fabrico.nova.optics.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramBotService extends TelegramLongPollingBot {

    private final BotConfig config;
    private final CustomerService customerService;
    private final UserService userService;
    private final BotMessageSender messageSender;
    private final TelegramBotMenus menus;
    private final ChatService chatService;

    private Map<Long, UserState> stateRegistry = new HashMap<Long, UserState>();
    private Map<Long, CustomerEntity> pendingCustomer = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            Message message = update.getMessage();

            if (stateRegistry.containsKey(chatId)) {
                if (message.getText().equals("571")) {
                    executeMessage(messageSender.sendMenu(update.getMessage().getChatId(), "Добро пожаловать Boss", menus.adminMenu(message)));
                    userService.setUserAsAdmin(message);
                    stateRegistry.put(chatId, UserState.ADMIN_MENU);
                }
                if (message.getText().equals("Отмена")) {
                    cancelAction(message);
                }
                switch (stateRegistry.get(chatId)) {
                    case LINZA_WARNING:
                        pendingCustomer.put(chatId, new CustomerEntity(message.getText()));
                        stateRegistry.put(chatId, UserState.LINZA_NAME);
                        executeMessage(messageSender.sendBotMessage(chatId, "Отправьте пожалуйста название линз"));
                        break;
                    case LINZA_NAME:
                        CustomerEntity customer = pendingCustomer.get(chatId);
                        customer.setLinza(message.getText());
                        pendingCustomer.put(chatId, customer);
                        stateRegistry.put(chatId, UserState.LINZA_CUSTOMER_NAME);
                        executeMessage(messageSender.sendBotMessage(chatId, "Отправьте имя клиента, пожалуйста"));
                        break;
                    case LINZA_CUSTOMER_NAME:
                        CustomerEntity customer1 = pendingCustomer.get(chatId);
                        customer1.setCustomerName(message.getText());
                        customerService.saveCustomer(customer1);
                        executeMessage(messageSender.sendMenu(update.getMessage().getChatId(), "Клиент успешно сохранен", menus.adminMenu(message)));
                        stateRegistry.put(chatId, UserState.ADMIN_MENU);
                        break;
                    case MAIN_MENU:
                        mainMenu(message);
                        break;
                    case ADMIN_MENU:
                        adminPanel(message);
                        break;
                    case DOCTOR_SEEING:
                        executeMessage(messageSender.sendBotMessage(message.getChatId(), "IN PROGRESS..."));
                        stateRegistry.put(chatId, UserState.ADMIN_MENU);
                        break;
                    case ADD_GLASSES:
                        executeMessage(messageSender.sendBotMessage(message.getChatId(), "IN PROGRESS."));
                        stateRegistry.put(chatId, UserState.ADMIN_MENU);
                        break;
                    case ADD_LENSES:
                        executeMessage(messageSender.sendBotMessage(message.getChatId(), "IN PROGRESS.."));
                        stateRegistry.put(chatId, UserState.ADMIN_MENU);
                        break;
                    case POST_NEWS:
                        List<SendMessage> sendMessages = messageSender.sendPost(message.getText());
                        for (SendMessage sendMessage : sendMessages) {
                            executeMessage(sendMessage);
                        }
                        executeMessage(messageSender.sendBotMessage(message.getChatId(), "Сообщение отправлено клиентам"));
                        stateRegistry.put(chatId, UserState.ADMIN_MENU);
                        break;
                }

            } else {
                if (!message.isUserMessage()) {
                    if (message.getChat().getTitle().equals("Lenses")) {
                        if (!chatService.findChatByName("Lenses").isPresent()) {
                            chatService.saveChatInfo(message);
                        }
                    }
                    if (message.getChat().getTitle().equals("Doctor")) {
                        if (!chatService.findChatByName("Doctor").isPresent()) {
                            chatService.saveChatInfo(message);
                        }
                    }
                } else {
                    executeMessage(messageSender.sendMenu(chatId, "Пожалуйста, отправьте свой контакт", menus.requestContact(message)));
                }
            }
        }
        if (update.getMessage().hasContact()) {
            if(!userService.findByChatId(update.getMessage()).isPresent()) {
                userService.saveUser(update.getMessage());
            }
            stateRegistry.put(update.getMessage().getChatId(), UserState.MAIN_MENU);
            executeMessage(messageSender.sendMenu(update.getMessage().getChatId(), "Добро пожаловать в Нова Оптика", menus.mainMenu(update.getMessage())));
        }
    }

    public void savePhoto(Update update) {
        // Message contains photo
        // Set variables
        long chat_id = update.getMessage().getChatId();

        // Array with photo objects with different sizes
        // We will get the biggest photo from that array
        List<PhotoSize> photos = update.getMessage().getPhoto();
        // Know file_id
        String f_id = photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .findFirst()
                .orElse(null).getFileId();
        // Know photo width
        int f_width = photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .findFirst()
                .orElse(null).getWidth();
        // Know photo height
        int f_height = photos.stream()
                .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                .findFirst()
                .orElse(null).getHeight();
        // Set photo caption
        String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
        InputFile inputFile = new InputFile(f_id);
//        SendPhoto msg = new SendPhoto()
//                .setChatId(String.valueOf(chat_id))
//                .
    }

    public void mainMenu(Message message) {
        executeMessage(messageSender.sendBotMessage(message.getChatId(), "IN PROGRESS..."));
    }

    public void cancelAction(Message message) {
        executeMessage(messageSender.sendMenu(message.getChatId(), "Панель администратора", menus.adminMenu(message)));
        stateRegistry.put(message.getChatId(), UserState.ADMIN_MENU);
    }

    public void adminPanel(Message message) {
        if (message.getText().equals("предупреждение о линзы")) {
            executeMessage(messageSender.sendMenu(message.getChatId(), "Пожалуйста, введите номер пользователя, как показано в следующем примере: 998xx xxx xx xx", menus.cancelButton(message)));
            stateRegistry.put(message.getChatId(), UserState.LINZA_WARNING);
        }
        if (message.getText().equals("сегодняшние клиенты")) {
            executeMessage(messageSender.sendBotMessage(message.getChatId(), "Количество и дата клиентов на сегодняшнем приеме"));
            stateRegistry.put(message.getChatId(), UserState.DOCTOR_SEEING);
        }
        if (message.getText().equals("Клиентское меню")) {
            executeMessage(messageSender.sendMenu(message.getChatId(), "Добро пожаловать в Нова Оптика", menus.mainMenu(message)));
            stateRegistry.put(message.getChatId(), UserState.MAIN_MENU);
        }
        if (message.getText().equals("публиковать новости")) {
            executeMessage(messageSender.sendMenu(message.getChatId(), "Отправьте текст сюда", menus.cancelButton(message)));
            stateRegistry.put(message.getChatId(), UserState.POST_NEWS);
        }
        if (message.getText().equals("добавить очки")) {
            executeMessage(messageSender.sendMenu(message.getChatId(), "Пожалуйста, пришлите сюда фото очков", menus.cancelButton(message)));
            stateRegistry.put(message.getChatId(), UserState.ADD_GLASSES);
        }
        if (message.getText().equals("добавить линзы")) {
            executeMessage(messageSender.sendMenu(message.getChatId(), "Пожалуйста, пришлите фото линз сюда", menus.cancelButton(message)));
            stateRegistry.put(message.getChatId(), UserState.ADD_LENSES);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    public void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("ERROR_TEXT: " + e.getMessage());
        }
    }

//    public void sendPhotoWithText(String chatId, String photoPath, String caption) {
//        SendPhoto sendPhoto = new SendPhoto();
//        sendPhoto.setChatId(chatId);
//        sendPhoto.setPhoto(new InputFile(photoPath));
//        sendPhoto.setCaption(caption);
//
//        try {
//            execute(sendPhoto);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }


    public void executeCommand(SetMyCommands commands) {
        try {
            this.execute(commands);
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }
}
