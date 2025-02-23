package com.weatherSDK.exceptions.api.utils;

public class RestQueryExecutorServerReturnedAnException extends RuntimeException {
    public RestQueryExecutorServerReturnedAnException(String message) {
        super(message);
    }

    public RestQueryExecutorServerReturnedAnException() {
    }
}
