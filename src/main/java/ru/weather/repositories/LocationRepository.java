package ru.weather.repositories;

import ru.weather.models.Location;

public class LocationRepository extends BaseRepository<Integer, Location> {
    public LocationRepository() {
        super(Location.class);
    }
}
