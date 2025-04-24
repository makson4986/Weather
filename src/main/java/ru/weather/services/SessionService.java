package ru.weather.services;

import lombok.RequiredArgsConstructor;
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

    public Session createSession(User user) {
        Session session = Session.builder()
                .user(user)
                .expiresAt(LocalDateTime.now().plusDays(14))
                .build();

        return sessionRepository.save(session);
    }

    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }

    public Optional<Session> findById(UUID id) {
        return sessionRepository.findById(id);
    }

    public boolean isCorrectSessionId(String cookieSessionId, User user) {
        return cookieSessionId == null ||
                cookieSessionId.isBlank() ||
                sessionRepository.findSessionsByLogin(cookieSessionId, user.getLogin()).isEmpty();
    }

    public boolean isSessionExpired(Session session) {
        return LocalDateTime.now().isAfter(session.getExpiresAt());
    }
}
