package pl.coderslab.charity.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.coderslab.charity.user.User;
import pl.coderslab.charity.user.UserService;

import java.util.UUID;

public class ResetPasswordListener implements ApplicationListener<OnResetPasswordEvent> {
    @Autowired
    private UserService service;
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void onApplicationEvent(OnResetPasswordEvent event) {
        this.resetPassword(event);
    }
    private void resetPassword(OnResetPasswordEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Reset Password Confirmation";
        String confirmationUrl = event.getAppUrl() + "/resetPasswordConfirm?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("\r\n" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }
}
