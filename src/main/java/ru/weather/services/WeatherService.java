package ru.weather.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.weather.dto.LocationDto;
import ru.weather.dto.NewLocationDto;
import ru.weather.mappers.LocationMapper;
import ru.weather.models.Location;
import ru.weather.repositories.LocationRepository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    @Value("${api.key}")
    private String API_KEY;
    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;

    @SneakyThrows
    public List<LocationDto> searchLocationByName(String name) {
        final String query = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=%s".formatted(name, API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(query))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<>() {});
    }

    public void addLocation(NewLocationDto locationDto) {
        Location location = locationMapper.toLocation(locationDto);
        locationRepository.save(location);
    }
}
