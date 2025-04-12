package ru.weather.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.weather.dto.UserDto;
import ru.weather.mappers.UserMapper;
import ru.weather.models.User;
import ru.weather.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public boolean isUserExist(String username) {
        return userRepository.findByLogin(username).isPresent();
    }
}
