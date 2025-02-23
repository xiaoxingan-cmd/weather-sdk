package com.weatherSDK.exceptions.api.utils;

public class RestQueryExecutorTooManyQueriesException extends RuntimeException {
    public RestQueryExecutorTooManyQueriesException(String message) {
        super(message);
    }

    public RestQueryExecutorTooManyQueriesException() {
    }
}
