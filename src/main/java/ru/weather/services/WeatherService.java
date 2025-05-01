package ru.weather.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.weather.dto.IdentifiedLocationDto;
import ru.weather.dto.LocationDto;
import ru.weather.dto.LocationJsonDto;
import ru.weather.dto.WeatherDto;
import ru.weather.mappers.IdentifiedLocationMapper;
import ru.weather.mappers.LocationMapper;
import ru.weather.models.Location;
import ru.weather.models.User;
import ru.weather.repositories.LocationRepository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {
    @Value("${api.key}")
    private String API_KEY;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final LocationMapper locationMapper;
    private final IdentifiedLocationMapper identifiedLocationMapper;
    private final LocationRepository locationRepository;

    @SneakyThrows
    public List<LocationJsonDto> searchLocationByName(String name) {
        String query = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1000&appid=%s"
                .formatted(name, API_KEY)
                .replace(" ", "%20");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(query))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), new TypeReference<>() {
        });
    }

    public void addLocation(LocationDto locationDto) {
        Location location = locationMapper.toLocation(locationDto);
        locationRepository.save(location);
    }

    public List<WeatherDto> getWeatherByLogin(String login) {
        List<IdentifiedLocationDto> locations = getLocationsByLogin(login);
        return locations.stream()
                .map(this::getWeatherLocation)
                .toList();
    }

    public void deleteLocationById(Integer id) {
        locationRepository.deleteById(id);
    }

    public boolean isLocationIdBelongsToUser(Integer id, User user) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        return locationOptional.map(location -> location.getUser().equals(user)).orElse(false);
    }

    private List<IdentifiedLocationDto> getLocationsByLogin(String login) {
        return locationRepository.getLocationsByLogin(login).stream()
                .map(identifiedLocationMapper::toIdentifiedLocationDto)
                .toList();
    }

    @SneakyThrows
    private WeatherDto getWeatherLocation(IdentifiedLocationDto location) {
        final String query = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric"
                .formatted(location.latitude(), location.longitude(), API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(query))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectNode jsonResponse = (ObjectNode) objectMapper.readTree(response.body());
        jsonResponse.put("id", location.id());
        jsonResponse.put("name", location.name());
        String stringResponse = objectMapper.writeValueAsString(jsonResponse);

        return objectMapper.readValue(stringResponse, WeatherDto.class);
    }
}
