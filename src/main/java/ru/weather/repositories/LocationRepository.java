package ru.weather.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.weather.exceptions.DataBaseException;
import ru.weather.models.Location;
import ru.weather.models.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LocationRepository extends BaseRepository<Integer, Location> {
    public LocationRepository() {
        super(Location.class);
    }

    @Transactional
    public List<Location> getLocationsByLogin(String login) {
        try {
            return entityManager.createQuery("""
                            SELECT l
                            FROM Location l
                            JOIN User u ON l.user.id = u.id
                            WHERE u.login = :login""", Location.class)
                    .setParameter("login", login)
                    .getResultList();
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }
}
