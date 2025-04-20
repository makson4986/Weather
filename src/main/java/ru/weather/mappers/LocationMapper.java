package ru.weather.mappers;

import org.mapstruct.Mapper;
import ru.weather.dto.LocationDto;
import ru.weather.models.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toLocation(LocationDto locationDto);

    LocationDto toLocationDto(Location location);
}
