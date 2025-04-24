package ru.weather.interceptors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.weather.models.Session;
import ru.weather.services.SessionService;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> cookieOptional = Arrays.stream(cookies).
                filter(cookie -> cookie.getName().equals("sessionId"))
                .findFirst();

        if (cookieOptional.isEmpty()) {
            response.sendRedirect("/signup");
            return false;
        }

        Optional<Session> sessionOptional;

        try {
            sessionOptional = sessionService.findById(UUID.fromString(cookieOptional.get().getValue()));
        } catch (IllegalArgumentException e) {
            response.sendRedirect("/signup");
            return false;
        }

        if (sessionOptional.isEmpty()) {
            response.sendRedirect("/signup");
            return false;
        }

        Session session = sessionOptional.get();

        if (sessionService.isSessionExpired(session)) {
            response.sendRedirect("/signup");
            return false;
        }

        request.setAttribute("user", session.getUser());
        request.setAttribute("session", session);
        return true;
    }
}
