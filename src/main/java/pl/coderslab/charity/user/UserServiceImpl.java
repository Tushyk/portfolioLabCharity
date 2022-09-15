package pl.coderslab.charity.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.Dto.UserDto;
import pl.coderslab.charity.error.UserAlreadyExistException;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;

    }
    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public User saveUser(UserDto userDto) throws UserAlreadyExistException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("konto o takim emailu juz isnieje: "
                    + userDto.getEmail());
        }
        if (usernameExists(userDto.getUsername())) {
            throw new UserAlreadyExistException("konto o takim loginie juz istnieje: "
                    + userDto.getUsername());
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setEnabled(0);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
        return user;
    }
    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken();
        myToken.setUser(user);
        myToken.setToken(token);
        tokenRepository.save(myToken);
    }
    @Override
    public void saveAdmin(UserDto userDto) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
    @Override
    public void saveSuperAdmin(UserDto userDto) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_SUPER-ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null; // proba sprawdzania czy konto istnieje
    }
    @Override
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null; // proba sprawdzania czy konto istnieje
    }
    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
}
