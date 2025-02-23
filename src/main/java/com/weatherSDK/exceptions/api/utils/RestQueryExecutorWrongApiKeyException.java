package com.weatherSDK.exceptions.api.utils;

public class RestQueryExecutorWrongApiKeyException extends RuntimeException {
    public RestQueryExecutorWrongApiKeyException(String message) {
        super(message);
    }

    public RestQueryExecutorWrongApiKeyException() {
    }
}
