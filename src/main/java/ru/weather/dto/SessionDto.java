package ru.weather.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.weather.models.User;

import java.util.UUID;

@Data
@NoArgsConstructor
public class SessionDto {
    private UUID id;
    private User user;

}
