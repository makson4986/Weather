package ru.weather.mappers;

import org.mapstruct.Mapper;
import ru.weather.dto.UserDto;
import ru.weather.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto userDto);
}
