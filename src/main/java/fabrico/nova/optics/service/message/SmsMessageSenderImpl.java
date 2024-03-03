package fabrico.nova.optics.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsMessageSenderImpl implements SmsMessageSender {


    @Override
    public void sendMessage(String toUser, String messageText) {
    }
}
