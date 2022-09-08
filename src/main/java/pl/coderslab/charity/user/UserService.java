package pl.coderslab.charity.user;

public interface UserService {
    User findByUserName(String name);
    User saveUser(User user);

    void createVerificationToken(User user, String token);

    void saveAdmin(User user);

    void saveSuperAdmin(User user);

    boolean emailExists(String email);

    boolean usernameExists(String username);
    VerificationToken getVerificationToken(String VerificationToken);

}
