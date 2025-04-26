package ru.weather.dto;

import ru.weather.models.User;

import java.math.BigDecimal;


public record IdentifiedLocationDto(
        Integer id,
        String name,
        User user,
        BigDecimal latitude,
        BigDecimal longitude) {
}
