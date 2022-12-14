package pl.coderslab.charity.user;

import pl.coderslab.charity.Dto.UserDto;
import pl.coderslab.charity.error.UserAlreadyExistException;

public interface UserService {
    User findByUserName(String name);

    User saveUser(UserDto userDto) throws UserAlreadyExistException;

    void deleteUser(Long id, String password, CurrentUser user);

    User editUser(UserDto userDto) throws UserAlreadyExistException;

    void createVerificationToken(User user, String token);

    void saveAdmin(UserDto userDto);

    void saveSuperAdmin(UserDto userDto);

    void  blockUser(Long id);

    void  unblockUser(Long id);

    boolean emailExists(String email);

    boolean usernameExists(String username);
    VerificationToken getVerificationToken(String VerificationToken);

}
