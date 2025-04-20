package ru.weather.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.weather.models.User;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class LocationDto {
    private String name;
    private User user;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
