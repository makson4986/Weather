package ru.weather.repositories;

import org.springframework.stereotype.Repository;
import ru.weather.models.User;

@Repository
public class UserRepository extends BaseRepository<Integer, User> {
    public UserRepository() {
        super(User.class);
    }
}
