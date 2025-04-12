package ru.weather.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.weather.mappers.UserMapper;
import ru.weather.models.Session;
import ru.weather.models.User;
import ru.weather.repositories.SessionRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    public void createSession(User user) {
        Session session = Session.builder()
                .user(user)
                .expiresAt(LocalDateTime.now().plusDays(14))
                .build();

        sessionRepository.save(session);
    }

    public Session getSessionByUsername(String username) {
        return sessionRepository.getSessionByUsername(username);
    }
}
