package com.weatherSDK.exceptions.api.utils;

public class RestQueryExecutorWrongCityNameException extends RuntimeException {
    public RestQueryExecutorWrongCityNameException(String message) {
        super(message);
    }

    public RestQueryExecutorWrongCityNameException() {

    }
}
