package ru.weather.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.weather.exceptions.DataBaseException;
import ru.weather.models.User;

import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<Integer, User> {
    public UserRepository() {
        super(User.class);
    }

    @Transactional
    public Optional<User> findByLogin(String login) {
        try {
            User user = entityManager.createQuery("""
                            SELECT u 
                            FROM User u 
                            WHERE u.login = :login""", User.class)
                    .setParameter("login", login).getSingleResultOrNull();

            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }
}
