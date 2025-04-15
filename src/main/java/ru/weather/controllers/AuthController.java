package ru.weather.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.weather.dto.UserDto;
import ru.weather.models.Session;
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
        model.addAttribute("userDto", new UserDto());
        return "sign-up";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute UserDto userDto, HttpServletResponse resp) {
        Session session = authService.signUp(userDto);

        Cookie cookie = createCookie(session.getId().toString());
        resp.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String signIn(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "sign-in";
    }

    @PostMapping("/login")
    public String signIn(@CookieValue(value = "sessionId", required = false) String cookieSessionId, @ModelAttribute UserDto userDto, Model model, HttpServletResponse resp) {
        Session session = authService.signIn(userDto, cookieSessionId);
        String sessionId = session.getId().toString();

        if (!cookieSessionId.equals(sessionId)) {
            Cookie cookie = createCookie(sessionId);
            resp.addCookie(cookie);
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public void signOut() {

    }

    public Cookie createCookie(String sessionId) {
        Cookie cookie = new Cookie("sessionId", sessionId);
        cookie.setPath("/");
        cookie.setMaxAge(-1);

        return cookie;
    }
}
