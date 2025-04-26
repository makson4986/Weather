package ru.weather.mappers;

import org.mapstruct.Mapper;
import ru.weather.dto.RegistrationDto;
import ru.weather.models.User;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    User toUser(RegistrationDto registrationDto);
}
