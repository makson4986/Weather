package ru.weather.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.weather.models.User;
import ru.weather.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public boolean isUserExist(String username) {
        return userRepository.findByLogin(username).isPresent();
    }
}
