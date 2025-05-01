package ru.weather.utils;

import lombok.experimental.UtilityClass;
import ru.weather.exceptions.InvalidLocationNameException;
import ru.weather.exceptions.InvalidLoginException;
import ru.weather.exceptions.InvalidPasswordException;

@UtilityClass
public class DataValidator {
    public static void checkValidPassword(String password) {
        if (!password.matches(".{8,}")) {
            throw new InvalidPasswordException("The password length must be at least 8 characters.");
        } else if (!password.matches(".*[A-Z].*")) {
            throw new InvalidPasswordException("The password contains upper case letters.");
        } else if (!password.matches(".*[a-z].*")) {
            throw new InvalidPasswordException("The password contains lower case letters.");
        } else if (!password.matches(".*\\d.*")) {
            throw new InvalidPasswordException("The password contains digit characters.");
        } else if (!password.matches(".*[!@#$%^&*()_+=\\-].*")) {
            throw new InvalidPasswordException("The password contains special characters.");
        }
    }

    public static void checkValidLocationName(String name) {
        if (name.length() < 3 || name.length() > 32) {
            throw new InvalidLocationNameException("The location name must be between 3 and 32 characters");
        } else if (!name.substring(0,1).matches("[A-ZА-ЯЁ]")) {
            throw new InvalidLocationNameException("The location name must start with a capital letter");
        } else if (!name.matches("^[A-Za-zÀ-ÖØ-öø-ÿА-ЯЁа-яё'’ -]+$")) {
            throw new InvalidLocationNameException("The name contains invalid characters");
        }
    }

    public static void checkValidLogin(String login) {
        if (login.length() < 3 || login.length() > 64) {
            throw new InvalidLoginException("The login name must be between 3 and 64 characters");
        } else if (!login.substring(0,1).matches("[a-zA-Z]")) {
            throw new InvalidLoginException("The login name must start with a letter");
        } else if (!login.matches("^[a-zA-Z][a-zA-Z0-9_-]{3,64}$")) {
            throw new InvalidLoginException("The login can only contain letters, numbers, underscores and hyphens");
        }
    }
}
