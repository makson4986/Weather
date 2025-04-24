package ru.weather.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.weather.dto.UserDto;
import ru.weather.models.Session;
import ru.weather.services.AuthService;
import ru.weather.services.SessionService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final SessionService sessionService;

    @GetMapping("/signup")
    public String signUp() {
        return "registration-page";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute UserDto userDto, HttpServletResponse resp) {
        Session session = authService.signUp(userDto);

        Cookie cookie = createCookie(session.getId().toString());
        resp.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String signIn() {
        return "login-page";
    }

    @PostMapping("/login")
    public String signIn(@ModelAttribute UserDto userDto, HttpServletResponse resp) {
        Session session = authService.signIn(userDto);
        String sessionId = session.getId().toString();

        Cookie cookie = createCookie(sessionId);
        resp.addCookie(cookie);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String signOut(@RequestParam("session") Session session, HttpServletResponse resp) {
        Cookie cookie = createCookie("", 0);
        resp.addCookie(cookie);
        sessionService.deleteSession(session);

        return "redirect:/signup";
    }

    private Cookie createCookie(String sessionId) {
        return createCookie(sessionId, -1);
    }

    private Cookie createCookie(String sessionId, Integer maxAge) {
        Cookie cookie = new Cookie("sessionId", sessionId);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        return cookie;
    }
}
