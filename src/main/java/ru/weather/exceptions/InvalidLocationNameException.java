package ru.weather.exceptions;

public class InvalidLocationNameException extends RuntimeException {
    public InvalidLocationNameException() {
        super();
    }

    public InvalidLocationNameException(String message) {
        super(message);
    }

    public InvalidLocationNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLocationNameException(Throwable cause) {
        super(cause);
    }

    protected InvalidLocationNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
