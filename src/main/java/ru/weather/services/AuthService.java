package ru.weather.services;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.weather.dto.RegistrationDto;
import ru.weather.dto.LoginDto;
import ru.weather.exceptions.*;
import ru.weather.mappers.RegistrationMapper;
import ru.weather.models.Session;
import ru.weather.models.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final SessionService sessionService;
    private final RegistrationMapper registrationMapper;

    public Session signUp(RegistrationDto registrationDto) {
        if (userService.isUserExist(registrationDto.login())) {
            throw new UserAlreadyExistException("User with this login already exists!");
        }

        checkPassword(registrationDto.password(), registrationDto.repeatPassword());
        User user = registrationMapper.toUser(registrationDto);
        user.setPassword(hashPassword(registrationDto.password()));
        userService.createUser(user);

        return sessionService.createSession(user);
    }

    public Session signIn(LoginDto loginDto) {
        Optional<User> userOptional = userService.findByLogin(loginDto.login());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }

        User user = userOptional.get();

        if (!isCheckMathPassword(loginDto)) {
            throw new InvalidPasswordException("The password is incorrect!");
        }

        return sessionService.createSession(user);
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

    private boolean isCheckMathPassword(LoginDto loginDto) {
        String correctPassword = userService.findByLogin(loginDto.login()).orElseThrow().getPassword();
        return BCrypt.checkpw(loginDto.password(), correctPassword);
    }
}
