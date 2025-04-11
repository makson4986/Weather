package ru.weather.repositories;

import ru.weather.models.Session;

import java.util.UUID;

public class SessionRepository extends BaseRepository<UUID, Session> {
    public SessionRepository() {
        super(Session.class);
    }
}
