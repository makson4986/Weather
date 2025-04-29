package ru.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherDto(
        Integer id,
        String name,
        Sys sys,
        Main main,
        List<Weather> weather) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Sys(String country) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Main(
            @JsonProperty("temp") int temperature,
            @JsonProperty("feels_like") float feelsLike,
            float humidity) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Weather(
            String description,
            String icon) {
    }
}

