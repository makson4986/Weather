package ru.weather.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.weather.models.Session;
import ru.weather.models.User;
import ru.weather.repositories.SessionRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    @Value("${session.duration.days}")
    private int SESSION_DURATION_DAYS;

    public Session createSession(User user) {
        Session session = Session.builder()
                .user(user)
                .expiresAt(LocalDateTime.now().plusDays(SESSION_DURATION_DAYS))
                .build();

        return sessionRepository.save(session);
    }

    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }

    public Optional<Session> findById(UUID id) {
        return sessionRepository.findById(id);
    }

    public boolean isSessionExpired(Session session) {
        return LocalDateTime.now().isAfter(session.getExpiresAt());
    }
}
