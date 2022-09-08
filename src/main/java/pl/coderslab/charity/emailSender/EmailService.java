package pl.coderslab.charity.emailSender;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
