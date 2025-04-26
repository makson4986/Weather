package ru.weather.mappers;

import org.mapstruct.Mapper;
import ru.weather.dto.IdentifiedLocationDto;
import ru.weather.models.Location;

@Mapper(componentModel = "spring")
public interface IdentifiedLocationMapper {
    IdentifiedLocationDto toIdentifiedLocationDto(Location location);
}
