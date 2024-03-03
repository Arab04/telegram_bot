package fabrico.nova.optics.service.bot;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramBotMenus {

    public ReplyKeyboardMarkup requestContact(Message message) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton contactButton = new KeyboardButton("Контакт");
        contactButton.setRequestContact(true);
        keyboardRow.add(contactButton);

        keyboardRows.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }

//    public SetMyCommands adminMenu() {
//        List<BotCommand> listOfCommands = new ArrayList<>();
//        listOfCommands.add(new BotCommand("/linza", "предупреждение клиента о наборе линзы"));
//        listOfCommands.add(new BotCommand("/doctor", "доктор осматривает клиента на сегодня"));
//        listOfCommands.add(new BotCommand("/main-menu", "главное меню для клиентов"));
//        listOfCommands.add(new BotCommand("/admin-panel", "вернуться в панель администратора"));
//        listOfCommands.add(new BotCommand("/post", "публиковать новости"));
//        listOfCommands.add(new BotCommand("/cancel", "Отмена"));
//        return new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null);
//    }


    public ReplyKeyboardMarkup adminMenu(Message message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();

        KeyboardButton warnAboutLenses = new KeyboardButton("предупреждение о линзы");
        KeyboardButton addGlasses = new KeyboardButton("добавить очки");
        KeyboardButton doctorSeeing = new KeyboardButton("сегодняшние клиенты");
        KeyboardButton addLenses = new KeyboardButton("добавить линзы");
        KeyboardButton addPost = new KeyboardButton("публиковать новости");
        KeyboardButton mainMenu = new KeyboardButton("Клиентское меню");


        keyboardRow1.add(addGlasses);
        keyboardRow1.add(addLenses);
        keyboardRow2.add(doctorSeeing);
        keyboardRow2.add(addPost);
        keyboardRow3.add(warnAboutLenses);
        keyboardRow3.add(mainMenu);

        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup mainMenu(Message message) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardButton doctorSeeing = new KeyboardButton("осмотр врача");
        KeyboardButton glasses = new KeyboardButton("очки");
        KeyboardButton linza = new KeyboardButton("линзы");
        keyboardRow1.add(linza);
        keyboardRow1.add(glasses);
        keyboardRow2.add(doctorSeeing);

        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup cancelButton(Message message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow1 = new KeyboardRow();

        KeyboardButton cancel = new KeyboardButton("Отмена");

        keyboardRow1.add(cancel);

        keyboardRows.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;

    }
}
