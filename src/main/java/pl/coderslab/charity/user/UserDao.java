package pl.coderslab.charity.user;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDao {
    @PersistenceContext
    EntityManager entityManager;
    public List<User> findAll(){
        return entityManager
                .createQuery("select u from User u")
                .getResultList();
    }
    public void save(User user) {
        entityManager.persist(user);
    }
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }
    public void update(User user) {
        entityManager.merge(user);
    }
    public void delete(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user)); }

}
