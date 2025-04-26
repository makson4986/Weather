package ru.weather.dto;

public record RegistrationDto(
        String login,
        String password,
        String repeatPassword) {
}
