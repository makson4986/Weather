package ru.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.weather.models.User;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentifiedLocationDto {
    private Integer id;
    private String name;
    private User user;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
