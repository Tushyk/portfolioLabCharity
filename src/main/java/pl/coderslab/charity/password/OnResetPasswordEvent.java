package pl.coderslab.charity.password;

import lombok.*;
import org.springframework.context.ApplicationEvent;
import pl.coderslab.charity.user.User;

import java.util.Locale;
@Getter
@Setter
@ToString
public class OnResetPasswordEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private User user;
    public OnResetPasswordEvent(
            User user, Locale locale, String appUrl) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
