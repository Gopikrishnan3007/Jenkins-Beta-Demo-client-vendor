package com.rts.tap.exception;

public class CandidateDataFetchException extends RuntimeException {
    public CandidateDataFetchException(String message) {
        super(message);
    }

    public CandidateDataFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}

