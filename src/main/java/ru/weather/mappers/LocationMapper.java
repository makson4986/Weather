package ru.weather.mappers;

import org.mapstruct.Mapper;
import ru.weather.dto.NewLocationDto;
import ru.weather.models.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toLocation(NewLocationDto locationDto);
}
