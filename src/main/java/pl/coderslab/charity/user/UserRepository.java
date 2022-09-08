package pl.coderslab.charity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    @Query(value = "select * from users join users_roles ur on users.id = ur.user_id where ur.roles_id = 1", nativeQuery = true)
    List<User> findUsersByRoleId();
    @Query(value = "select * from users join users_roles ur on users.id = ur.user_id where ur.roles_id = 2", nativeQuery = true)
    List<User> findAdminsByRoleId();

}
