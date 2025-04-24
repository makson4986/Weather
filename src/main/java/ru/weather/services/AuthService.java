package ru.weather.services;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.weather.dto.UserDto;
import ru.weather.exceptions.*;
import ru.weather.mappers.UserMapper;
import ru.weather.models.Session;
import ru.weather.models.User;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final SessionService sessionService;
    private final UserMapper userMapper;

    public Session signUp(UserDto userDto) {
        if (userService.isUserExist(userDto.getLogin())) {
            throw new UserAlreadyExistException("User with this login already exists!");
        }

        checkPassword(userDto.getPassword(), userDto.getRepeatPassword());
        userDto.setPassword(hashPassword(userDto.getPassword()));

        User user = userMapper.toUser(userDto);
        userService.createUser(user);
        return sessionService.createSession(user);
    }

    public Session signIn(UserDto userDto, String cookieSessionId) {
        Optional<User> userOptional = userService.findByLogin(userDto.getLogin());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }

        User user = userOptional.get();

        if (!isCheckMathPassword(userDto)) {
            throw new InvalidPasswordException("The password is incorrect!");
        }

        if (sessionService.isCorrectSessionId(cookieSessionId, user)) {
            return sessionService.createSession(user);
        }

        Session session = sessionService.findById(UUID.fromString(cookieSessionId)).orElseThrow();

        if (sessionService.isSessionExpired(session)) {
            sessionService.deleteSession(session);
            return sessionService.createSession(user);
        }

        return session;
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
            throw new PasswordLengthException("Password must be at least 8 characters long");
        }
    }

    private boolean isCheckMathPassword(UserDto userDto) {
        String correctPassword = userService.findByLogin(userDto.getLogin()).orElseThrow().getPassword();
        return BCrypt.checkpw(userDto.getPassword(), correctPassword);
    }
}
