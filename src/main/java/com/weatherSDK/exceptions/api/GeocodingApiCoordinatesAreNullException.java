package com.weatherSDK.exceptions.api;

public class GeocodingApiCoordinatesAreNullException extends NullPointerException {
    public GeocodingApiCoordinatesAreNullException(String message) {
        super(message);
    }

    public GeocodingApiCoordinatesAreNullException() {
    }
}
