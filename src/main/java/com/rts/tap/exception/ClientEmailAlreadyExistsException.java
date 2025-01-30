package com.rts.tap.exception;

public class ClientEmailAlreadyExistsException extends RuntimeException {
    public ClientEmailAlreadyExistsException(String message) {
        super(message);
    }
}
