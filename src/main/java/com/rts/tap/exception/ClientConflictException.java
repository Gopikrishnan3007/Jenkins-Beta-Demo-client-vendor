package com.rts.tap.exception;

public class ClientConflictException extends RuntimeException {

    public ClientConflictException(String message) {
        super(message);
    }

    public ClientConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}