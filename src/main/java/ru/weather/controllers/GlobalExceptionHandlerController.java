package ru.weather.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.weather.exceptions.*;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlerController {
    private final Logger logger;

    @ExceptionHandler({
            PasswordsDoNotMatchException.class,
            UserAlreadyExistException.class,
            InvalidLoginException.class,
            InvalidPasswordException.class
    })
    public String handleSignUpException(Exception ex, Model model) {
        if (ex instanceof PasswordsDoNotMatchException || ex instanceof InvalidPasswordException) {
            model.addAttribute("passwordException", true);
        } else if (ex instanceof UserAlreadyExistException || ex instanceof InvalidLoginException) {
            model.addAttribute("loginException", true);
        }

        logger.warn(ex.getMessage(), ex);
        model.addAttribute("exceptionText", ex.getMessage());
        return "registration-page";
    }

    @ExceptionHandler({
            PasswordVerificationException.class,
            UserNotFoundException.class
    })
    public String handleSignInException(Exception ex, Model model) {
        if (ex instanceof PasswordVerificationException) {
            model.addAttribute("passwordException", true);
        } else if (ex instanceof UserNotFoundException) {
            model.addAttribute("loginException", true);
        }

        logger.warn(ex.getMessage(), ex);
        return "login-page";
    }

    @ExceptionHandler(InvalidLocationNameException.class)
    public String handleInvalidLocationNameException(Exception ex, Model model) {
        model.addAttribute("locationNameException", true);
        model.addAttribute("exceptionText", ex.getMessage());

        logger.warn(ex.getMessage(), ex);
        return "search-result";
    }

    @ExceptionHandler(Exception.class)
    public String handleOtherException(Exception ex, Model model) {
        if (ex instanceof DataBaseException) {
            logger.error("Database error occurred", ex);
        } else {
            logger.error(ex.getMessage(), ex);
        }

        model.addAttribute("exceptionText", ex);
        return "error-page";
    }
}
