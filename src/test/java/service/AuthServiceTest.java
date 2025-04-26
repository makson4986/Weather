package service;

import config.DataBaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.weather.dto.RegistrationDto;
import ru.weather.mappers.RegistrationMapper;
import ru.weather.models.Session;
import ru.weather.models.User;
import ru.weather.repositories.SessionRepository;
import ru.weather.repositories.UserRepository;
import ru.weather.services.AuthService;
import ru.weather.services.SessionService;
import ru.weather.services.UserService;

import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = DataBaseConfig.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    @Autowired
    private SessionRepository sessionRepository;
    @Mock
    private RegistrationMapper registrationMapper;
    private UserService userService;
    private SessionService sessionService;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        sessionService = new SessionService(sessionRepository);
        authService = new AuthService(userService, sessionService, registrationMapper);
    }

    @Test
    @DisplayName("Calling the register method should result in a new record being added to the Users table and a new session being created in the table")
    void ifUserRegisteredThenInTableUserAppearedRecord() {
        RegistrationDto registrationDto = new RegistrationDto("testLogin", "password", "password");

        Mockito.doReturn(Optional.empty()).when(userRepository).findByLogin(Mockito.anyString());
        Mockito.doReturn(new User()).when(registrationMapper).toUser(Mockito.any(RegistrationDto.class));

        Session session = authService.signUp(registrationDto);

        Mockito.verify(userRepository).save(Mockito.any(User.class));
        Mockito.verify(sessionRepository).save(Mockito.any(Session.class));
    }
}

