package ru.weather.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.weather.dto.UserDto;
import ru.weather.services.AuthService;
import ru.weather.services.SessionService;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final SessionService sessionService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("username", "test");
        return "index";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("RegistrationDto", new UserDto());
        return "sign-up";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute UserDto userDto, HttpServletResponse resp) {
        authService.signUp(userDto);

        String sessionId = sessionService.getSessionByUsername(userDto.getUsername()).getId().toString();

        Cookie cookie = new Cookie("sessionId", sessionId);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/login")
    public void signIn() {

    }

    @GetMapping("/logout")
    public void signOut() {

    }
}
