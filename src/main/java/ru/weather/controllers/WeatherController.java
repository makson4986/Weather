package ru.weather.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.weather.dto.LocationDto;
import ru.weather.dto.NewLocationDto;
import ru.weather.models.Session;
import ru.weather.models.User;
import ru.weather.services.SessionService;
import ru.weather.services.WeatherService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
    private final SessionService sessionService;

    @GetMapping("/")
    public String mainPage(@RequestAttribute("user") User user,
                           Model model) {
        model.addAttribute("user", user);
        return "main-page";

    }

    @GetMapping("/search-location")
    public String searchLocationByName(@RequestParam("name") String name,
                                       @RequestAttribute("user") User user,
                                       Model model) {
        List<LocationDto> locations = weatherService.searchLocationByName(name);
        model.addAttribute("user", user);
        model.addAttribute("locations", locations);

        return "search-result";
    }

    @PostMapping("/add-location")
    public String addLocation(@ModelAttribute NewLocationDto location,
                              @RequestAttribute("user") User user) {
        location.setUser(user);
        weatherService.addLocation(location);

        return "redirect:/main-page";
    }

    @PostMapping("/delete-location")
    public void deleteLocation() {

    }
}
