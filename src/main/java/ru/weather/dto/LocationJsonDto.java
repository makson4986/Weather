package ru.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


@JsonIgnoreProperties(ignoreUnknown = true)
public record LocationJsonDto(
        String name,
        @JsonProperty("lat") BigDecimal latitude,
        @JsonProperty("lon") BigDecimal longitude,
        String country,
        String state) {
}
