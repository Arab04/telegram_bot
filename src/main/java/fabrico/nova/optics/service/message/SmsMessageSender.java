package fabrico.nova.optics.service.message;

public interface SmsMessageSender {


    void sendMessage(String to, String message);
}
