package com.rts.tap.exception;

public class InterviewDataFetchException extends RuntimeException {
    public InterviewDataFetchException(String message) {
        super(message);
    }

    public InterviewDataFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}