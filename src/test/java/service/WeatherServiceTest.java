package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.DataBaseConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.weather.services.WeatherService;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = DataBaseConfig.class)
public class WeatherServiceTest {
    @Mock
    private HttpClient httpClient;
    @Spy
    private ObjectMapper objectMapper;
    @InjectMocks
    private WeatherService weatherService;

    @Test
    @SneakyThrows
    @DisplayName("Check if the response from OpenWeather API contains the expected location")
    void checkResponseThatContainsExpectedLocation() {
        final String response = """
                [ {
                  "name" : "Moscow",
                  "lat" : 55.7504461,
                  "lon" : 37.6174943,
                  "country" : "RU",
                  "state" : "Moscow"
                }, {
                  "name" : "Moscow",
                  "local_names" : {
                    "en" : "Moscow",
                    "ru" : "Москва"
                  },
                  "lat" : 46.7323875,
                  "lon" : -117.0001651,
                  "country" : "US",
                  "state" : "Idaho"
                }, {
                  "name" : "Moscow",
                  "lat" : 45.071096,
                  "lon" : -69.891586,
                  "country" : "US",
                  "state" : "Maine"
                }, {
                  "name" : "Moscow",
                  "lat" : 35.0619984,
                  "lon" : -89.4039612,
                  "country" : "US",
                  "state" : "Tennessee"
                }, {
                  "name" : "Moscow",
                  "lat" : 39.5437014,
                  "lon" : -79.0050273,
                  "country" : "US",
                  "state" : "Maryland"
                } ]
                """;
        var mockResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(mockResponse.body()).thenReturn(response);
        Mockito.doReturn(mockResponse).when(httpClient).send(Mockito.any(), Mockito.any());

        var locations = weatherService.searchLocationByName("Moscow");
        Assertions.assertTrue(locations.stream().anyMatch(location -> "Moscow".equals(location.name())));
    }
}
