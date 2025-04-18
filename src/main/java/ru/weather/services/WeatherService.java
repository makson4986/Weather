package ru.weather.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.weather.dto.LocationDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class WeatherService {
    private final String API_KEY;
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public WeatherService(@Value("${api.key}") String apiKey, HttpClient httpClient, ObjectMapper mapper) {
        API_KEY = apiKey;
        this.httpClient = httpClient;
        this.mapper = mapper;
    }

    @SneakyThrows
    public List<LocationDto> searchLocationByName(String name) {
        final String query = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=%s".formatted(name, API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(query))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<List<LocationDto>>() {});
    }
}
