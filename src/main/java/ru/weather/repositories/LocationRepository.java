package ru.weather.repositories;

import org.springframework.stereotype.Repository;
import ru.weather.models.Location;

@Repository
public class LocationRepository extends BaseRepository<Integer, Location> {
    public LocationRepository() {
        super(Location.class);
    }
}
