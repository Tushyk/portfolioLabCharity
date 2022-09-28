package pl.coderslab.charity.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import pl.coderslab.charity.Dto.UserDto;
import pl.coderslab.charity.error.UserNotFoundException;
import pl.coderslab.charity.user.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Controller
public class passwordController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    public passwordController(UserRepository userRepository, UserService userService, TokenRepository tokenRepository, BCryptPasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @GetMapping("/resetPassword")
    public String resetPasswordForm() {
        return "reset-password";
    }
    @GetMapping("/resetPassword/sendingEmail")
    public String resettingPassword(HttpServletRequest request, Model model,@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }
        String appUrl = request.getContextPath();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Reset Password Confirmation";
        String confirmationUrl = appUrl + "/resetPasswordConfirm?token=" + token;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("konto.do.aplikacji.spring@gmail.com");
        mail.setTo(recipientAddress);
        mail.setSubject(subject);
        mail.setText("\r\n" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(mail);

        return "confirm-resetPassword";
    }
    @GetMapping("/resetPasswordConfirm")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token){
        Locale locale = request.getLocale();
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            return "redirect:/badpassword.html?lang=" + locale.getLanguage();
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "redirect:/dupabadUser.html?lang=" + locale.getLanguage();
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        model.addAttribute("user", userDto);
        return "reset-password-form";
    }
    @PostMapping("/resetPasswordConfirm")
    public String confirmRegistration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result){
        if (result.hasErrors()){
            User user = userRepository.findById(userDto.getId()).orElseThrow(EntityNotFoundException::new);
            tokenRepository.delete(tokenRepository.findByUser(user));
            return "reset-password-form";
        }
        User user = userRepository.findById(userDto.getId()).orElseThrow(EntityNotFoundException::new);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        VerificationToken token = tokenRepository.findByUser(user);
        if (token != null) {
            tokenRepository.delete(tokenRepository.findByUser(user));
        }
        userRepository.save(user);
        return "redirect:/login";
    }
}
