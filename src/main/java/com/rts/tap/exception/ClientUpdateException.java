package com.rts.tap.exception;


public class ClientUpdateException extends RuntimeException {

    public ClientUpdateException(String message) {
        super(message);
    }

    public ClientUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
