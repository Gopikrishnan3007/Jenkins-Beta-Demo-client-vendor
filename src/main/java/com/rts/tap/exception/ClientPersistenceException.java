package com.rts.tap.exception;

public class ClientPersistenceException extends RuntimeException {
    public ClientPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}