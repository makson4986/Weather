package ru.weather.mappers;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.weather.dto.LocationDto;
import ru.weather.models.Location;
import ru.weather.models.User;
import ru.weather.services.UserService;

@Mapper(componentModel = "spring")
public abstract class LocationMapper {
    @Autowired
    protected UserService userService;

    @Mapping(target = "user", source = "userLogin")
    public abstract Location toLocation(LocationDto dto);

    protected User map(String userLogin) {
        if (userLogin == null) {
            return null;
        }
        return userService.findByLogin(userLogin)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

