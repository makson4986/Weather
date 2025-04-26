package ru.weather.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.weather.dto.LocationDto;
import ru.weather.dto.LocationJsonDto;
import ru.weather.dto.WeatherDto;
import ru.weather.models.Session;
import ru.weather.services.WeatherService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/")
    public String mainPage(@RequestAttribute("session") Session session,
                           Model model) {
        String login = session.getUser().getLogin();
        List<WeatherDto> weather = weatherService.getWeatherByLogin(login);
        model.addAttribute("weather", weather);
        model.addAttribute("userLogin", login);

        return "main-page";

    }

    @GetMapping("/search-location")
    public String searchLocationByName(@RequestParam("name") String name,
                                       @RequestAttribute("session") Session session,
                                       Model model) {
        String login = session.getUser().getLogin();
        List<LocationJsonDto> locations = weatherService.searchLocationByName(name);
        model.addAttribute("userLogin", login);
        model.addAttribute("locations", locations);

        return "search-result";
    }

    @PostMapping("/add-location")
    public String addLocation(@ModelAttribute LocationDto location) {
        weatherService.addLocation(location);
        return "redirect:/";
    }

    @PostMapping("/delete-location")
    public String deleteLocation(@RequestParam("id") Integer id,
                                 @RequestAttribute("session") Session session) {
        if (weatherService.isLocationIdBelongsToUser(id, session.getUser())) {
            weatherService.deleteLocationById(id);
        }

        return "redirect:/";
    }
}
