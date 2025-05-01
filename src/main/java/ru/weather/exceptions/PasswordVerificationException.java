package ru.weather.exceptions;

public class PasswordVerificationException extends RuntimeException {
    public PasswordVerificationException() {
        super();
    }

    public PasswordVerificationException(String message) {
        super(message);
    }

    public PasswordVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordVerificationException(Throwable cause) {
        super(cause);
    }

    protected PasswordVerificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
