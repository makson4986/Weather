package ru.weather.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.weather.dto.LocationDto;
import ru.weather.services.WeatherService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("login", "TODO");
        return "main-page";

    }

    @GetMapping("/search-location")
    public String searchLocationByName(@RequestParam("name") String name, Model model) {
        List<LocationDto> locations = weatherService.searchLocationByName(name);
        model.addAttribute("locations", locations);
        return "search-result";
    }

    @PostMapping("/add-location")
    public void addLocation() {

    }

    @PostMapping("/delete-location")
    public void deleteLocation() {

    }
}
