package ru.weather.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.weather.models.Session;

import java.util.UUID;

@Repository
public class SessionRepository extends BaseRepository<UUID, Session> {
    public SessionRepository() {
        super(Session.class);
    }

    @Transactional
    public Session getSessionByUsername(String username) {
        return entityManager.createQuery("""
                SELECT s
                FROM Session s
                JOIN User u ON s.user.id = u.id
                WHERE u.login = :username""", Session.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
