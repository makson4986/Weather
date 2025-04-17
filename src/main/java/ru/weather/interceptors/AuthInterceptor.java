package ru.weather.interceptors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> cookieOptional = Arrays.stream(cookies).
                filter(cookie -> cookie.getName().equals("sessionId"))
                .findFirst();

        if (cookieOptional.isEmpty()) {
            request.getRequestDispatcher(request.getContextPath() + "/error").forward(request, response);
            return false;
        }

        return true;
    }
}
