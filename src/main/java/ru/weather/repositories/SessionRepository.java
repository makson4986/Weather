package ru.weather.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.weather.exceptions.DataBaseException;
import ru.weather.models.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionRepository extends BaseRepository<UUID, Session> {
    public SessionRepository() {
        super(Session.class);
    }

    @Transactional
    public Optional<Session> findSessionsByLogin(String sessionId, String login) {
        try {
            Session session = entityManager.createQuery("""
                            SELECT s 
                            FROM Session s
                            JOIN User u ON s.user.id = u.id
                            WHERE u.login = :login AND s.id = :sessionId""", Session.class)
                    .setParameter("login", login)
                    .setParameter("sessionId", UUID.fromString(sessionId))
                    .getSingleResultOrNull();

            return Optional.ofNullable(session);
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }
}
