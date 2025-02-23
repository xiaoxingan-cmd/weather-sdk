package com.weatherSDK.exceptions.weatherClient;

public class WeatherClientApiKeyDuplicatedException extends RuntimeException {
    public WeatherClientApiKeyDuplicatedException(String message) {
        super(message);
    }

    public WeatherClientApiKeyDuplicatedException() {
    }
}
