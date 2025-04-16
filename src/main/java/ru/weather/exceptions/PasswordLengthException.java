package ru.weather.exceptions;

public class PasswordLengthException extends RuntimeException {
    public PasswordLengthException() {
        super();
    }

    public PasswordLengthException(String message) {
        super(message);
    }

    public PasswordLengthException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordLengthException(Throwable cause) {
        super(cause);
    }

    protected PasswordLengthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
