package ru.weather.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.weather.dto.LocationDto;
import ru.weather.dto.LocationJsonDto;
import ru.weather.dto.WeatherDto;
import ru.weather.models.User;
import ru.weather.services.WeatherService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    @GetMapping("/")
    public String mainPage(@RequestAttribute("user") User user,
                           Model model) {
        List<WeatherDto> weather = weatherService.getWeatherByLogin(user.getLogin());
        model.addAttribute("weather", weather);
        model.addAttribute("user", user);

        return "main-page";

    }

    @GetMapping("/search-location")
    public String searchLocationByName(@RequestParam("name") String name,
                                       @RequestAttribute("user") User user,
                                       Model model) {
        List<LocationJsonDto> locations = weatherService.searchLocationByName(name);
        model.addAttribute("user", user);
        model.addAttribute("locations", locations);

        return "search-result";
    }

    @PostMapping("/add-location")
    public String addLocation(@ModelAttribute LocationDto location,
                              @RequestAttribute("user") User user) {
        location.setUser(user);
        weatherService.addLocation(location);

        return "redirect:/";
    }

    @PostMapping("/delete-location")
    public void deleteLocation() {

    }
}
