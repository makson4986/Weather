package ru.weather.dto;

import java.math.BigDecimal;


public record LocationDto(
        String name,
        String  userLogin,
        BigDecimal latitude,
        BigDecimal longitude) {
}
