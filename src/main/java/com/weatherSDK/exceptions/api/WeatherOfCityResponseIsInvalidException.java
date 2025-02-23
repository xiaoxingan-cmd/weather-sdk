package com.weatherSDK.exceptions.api;

public class WeatherOfCityResponseIsInvalidException extends RuntimeException {
    public WeatherOfCityResponseIsInvalidException(String message) {
        super(message);
    }

    public WeatherOfCityResponseIsInvalidException() {
    }
}
