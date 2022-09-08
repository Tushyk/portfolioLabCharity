package pl.coderslab.charity.user;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import pl.coderslab.charity.donation.Donation;
import pl.coderslab.charity.donation.DonationRepository;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DonationRepository donationRepository;
    private final TokenRepository tokenRepository;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    public UserController(UserService userService,
                          RoleRepository roleRepository,
                          UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder, DonationRepository donationRepository, TokenRepository tokenRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.donationRepository = donationRepository;
        this.tokenRepository = tokenRepository;
    }
    @GetMapping("/registration")
    public String registrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }
    @PostMapping("/registration")
    public String save(@Valid User user, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors() || !Objects.equals(user.getMatchingPassword(), user.getPassword())){
            return "registration";
        }
        if (userService.emailExists(user.getEmail()) || userService.usernameExists(user.getUsername())){
            return "registration";
        }
        else if (Objects.equals(user.getPassword(), "super")) {
            userService.saveSuperAdmin(user);
            return "login";
        } else {
            User registered = userService.saveUser(user);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale() ,appUrl));
        }
        return "confirm-registration";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/logout")
    public String logout() {
        return "index";
    }
    @GetMapping("/user/edit")
    public String edit(Model model, @AuthenticationPrincipal CurrentUser user) {
        model.addAttribute("login", user.getUser().getUsername());
        model.addAttribute("email", user.getUser().getEmail());
        return "edit-user";
    }
    @GetMapping("/user/update")
    public String update(@AuthenticationPrincipal CurrentUser currentUser,
                         @RequestParam String oldPassword,
                         @RequestParam String newPassword,
                         @RequestParam String repeatPassword,
                         @RequestParam String username,
                         @RequestParam String email) {

        currentUser.getUser().setUsername(RandomStringUtils.random(50,true,true));
        currentUser.getUser().setEmail("DFSDGSDVDGDSFBSBDFVDFSD@wp.pl");
        userRepository.save(currentUser.getUser());
        if (userService.emailExists(email) || userService.usernameExists(username)){
            return "redirect:/user/edit";
        }
        if (newPassword.equals(repeatPassword) && passwordEncoder.matches(oldPassword, currentUser.getUser().getPassword())) {
            currentUser.getUser().setPassword(passwordEncoder.encode(newPassword));
            currentUser.getUser().setUsername(username);
            currentUser.getUser().setEmail(email);
            userRepository.save(currentUser.getUser());
            return "redirect:/";
        } else {
            return "redirect:/user/edit";
        }
    }
    @GetMapping("/admin/user/list")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findUsersByRoleId());
        return "user-list";
    }
    @GetMapping("/super-admin/admin/list")
    public String adminList(Model model) {
        model.addAttribute("admins", userRepository.findAdminsByRoleId());
        return "admin-list";
    }
    @GetMapping("/admin/blockUser/{id}")
    public String blockUserAccount(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setEnabled(0);
        userRepository.save(user);
            return "redirect:/admin/user/list";
    }
    @GetMapping("/admin/unblockUser/{id}")
    public String unblockUserAccount(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setEnabled(1);
        userRepository.save(user);
            return "redirect:/admin/user/list";
    }
    @GetMapping("/super-admin/blockAdmin/{id}")
    public String blockAdminAccount(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setEnabled(0);
        userRepository.save(user);
        return "redirect:/super-admin/admin/list";
    }
    @GetMapping("/super-admin/unblockAdmin/{id}")
    public String unblockAdminAccount(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setEnabled(1);
        userRepository.save(user);
        return "redirect:/super-admin/admin/list";
    }
    @GetMapping("/super-admin/add/admin")
    public String addAdmin(Model model) {
        User user = new User();
        model.addAttribute("admin", user);
        return "admin-registration";
    }
    @PostMapping("/super-admin/add/admin")
    public String saveAdmin(@Valid User user, BindingResult result) {
        if (result.hasErrors() || !Objects.equals(user.getMatchingPassword(), user.getPassword())){
            return "admin-registration";
        }
        if (userService.emailExists(user.getEmail()) || userService.usernameExists(user.getUsername())){
            return "admin-registration";
        }
        userService.saveAdmin(user);
        return "redirect:/super-admin/admin/list";
    }
    @GetMapping("/super-admin/edit/admin/{id}")
    public String editAdmin(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser user) {
        model.addAttribute("admin", userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
        return "edit-admin";
    }
    @PostMapping("/super-admin/update/admin")
    public String updateAdmin(@Valid User user, BindingResult result) {
        if (result.hasErrors() || !Objects.equals(user.getMatchingPassword(), user.getPassword())){
            return "edit-admin";
        }
        if (userService.emailExists(user.getEmail()) || userService.usernameExists(user.getUsername())){
            return "edit-admin";
        }
            userService.saveAdmin(user);
            return "redirect:/super-admin/admin/list";
    }
    @GetMapping("/super-admin/deleteConfirm/admin/{id}")
    public String confirm(Model model, @PathVariable long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("userId", user.getId());
        return "confirm-delete-admin";
    }
    @GetMapping("/super-admin/delete/admin/{id}")
    @Transactional
    public String delete(@PathVariable long id, @RequestParam String password, @AuthenticationPrincipal CurrentUser user) {
        User admin = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (passwordEncoder.matches(password, user.getUser().getPassword())){
            admin.setRoles(null);
            List<Donation> donations = donationRepository.findAllByUserId(id);
            for (Donation donation : donations) {
                donation.setUser(null);
            }
            userRepository.delete(admin);
            return "redirect:/super-admin/admin/list";
        }
        return "redirect:/error";
    }
    @GetMapping("/admin/edit/user/{id}")
    public String editUser(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser user) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
        return "edit-user-byAdmin";
    }
    @PostMapping("/admin/update/user")
    public String updateUser(@Valid User user, BindingResult result) {
        if (result.hasErrors() || !Objects.equals(user.getMatchingPassword(), user.getPassword())){
            return "edit-user-byAdmin";
        }
        if (userService.emailExists(user.getEmail()) || userService.usernameExists(user.getUsername())){
            return "edit-user-byAdmin";
        }
        userService.saveUser(user);
        return "redirect:/admin/user/list";
    }
    @GetMapping("/admin/deleteConfirm/user/{id}")
    public String confirmDeleteUser(Model model, @PathVariable long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("userId", user.getId());
        return "confirm-delete-user";
    }
    @GetMapping("/admin/delete/user/{id}")
    @Transactional
    public String deleteUser(@PathVariable long id, @RequestParam String password, @AuthenticationPrincipal CurrentUser user) {
        User userToDelete = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (passwordEncoder.matches(password, user.getUser().getPassword())){
            userToDelete.setRoles(null);
            List<Donation> donations = donationRepository.findAllByUserId(id);
            for (Donation donation : donations) {
                donation.setUser(null);
            }
            userRepository.delete(userToDelete);
            return "redirect:/admin/user/list";
        }
        return "redirect:/error";
    }
    @GetMapping("/registrationConfirm")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token){
        Locale locale = request.getLocale();
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "redirect:/dupabadUser.html?lang=" + locale.getLanguage();
        }
        user.setEnabled(1);
        tokenRepository.delete(tokenRepository.findByUser(user));
        userRepository.save(user);
        return "redirect:/login";
    }
}
