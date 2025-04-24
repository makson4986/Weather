package ru.weather.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.weather.dto.UserDto;
import ru.weather.exceptions.*;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlerController {
    private final Logger logger;

    @ExceptionHandler({
            PasswordsDoNotMatchException.class,
            PasswordLengthException.class,
            UserAlreadyExistException.class
    })
    public String handleSignUpException(Exception ex, Model model) {
        if (ex instanceof PasswordsDoNotMatchException) {
            model.addAttribute("isPasswordDoNotMatchException", true);
        } else if (ex instanceof PasswordLengthException) {
            model.addAttribute("isPasswordLengthException", true);
        } else if (ex instanceof UserAlreadyExistException) {
            model.addAttribute("isUserAlreadyExistException", true);
        }

        logger.warn(ex.getMessage(), ex);
        return "registration-page";
    }

    @ExceptionHandler({
            InvalidPasswordException.class,
            UserNotFoundException.class
    })
    public String handleSignInException(Exception ex, Model model) {
        if (ex instanceof InvalidPasswordException) {
            model.addAttribute("isInvalidPasswordException", true);
        } else if (ex instanceof UserNotFoundException) {
            model.addAttribute("isUserNotFoundException", true);
        }

        logger.warn(ex.getMessage(), ex);
        return "login-page";
    }

    @ExceptionHandler(Exception.class)
    public String handleOtherException(Exception ex) {
        if (ex instanceof DataBaseException) {
            logger.error("Database error occurred", ex);
        } else {
            logger.error(ex.getMessage(), ex);
        }

        return "error-page";
    }
}
