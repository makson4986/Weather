package ru.weather.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.weather.dto.UserDto;
import ru.weather.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "login", source = "username")
    User toUser(UserDto userDto);
}
