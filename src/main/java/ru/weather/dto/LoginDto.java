package ru.weather.dto;

public record LoginDto(
        String login,
        String password,
        String repeatPassword) {
}
