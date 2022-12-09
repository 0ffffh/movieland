package com.movieland.exception;

public class CurrencyServiceException extends RuntimeException {
    public CurrencyServiceException(String message) {
        super(message);
    }

    public CurrencyServiceException(Throwable cause) {
        super(cause);
    }
}
