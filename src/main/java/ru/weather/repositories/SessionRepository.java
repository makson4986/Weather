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
}
