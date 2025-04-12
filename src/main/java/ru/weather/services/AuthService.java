package ru.weather.services;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.weather.dto.UserDto;
import ru.weather.exceptions.InvalidPasswordException;
import ru.weather.exceptions.PasswordsDoNotMatchException;
import ru.weather.exceptions.UserAlreadyExistException;
import ru.weather.mappers.UserMapper;
import ru.weather.models.User;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final SessionService sessionService;
    private final UserMapper userMapper;

    public void signUp(UserDto userDto) {
        if (userService.isUserExist(userDto.getUsername())) {
            throw new UserAlreadyExistException("User with this login already exists");
        }

        checkPassword(userDto.getPassword(), userDto.getRepeatPassword());
        userDto.setPassword(hashPassword(userDto.getPassword()));

        User user = userMapper.toUser(userDto);
        userService.createUser(user);
        sessionService.createSession(user);
    }


    private String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    private void checkPassword(String password, String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException("Passwords do not match");
        }

        if (password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters long");
        }
    }
}
