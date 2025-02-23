package com.weatherSDK.exceptions.weatherClient;

public class WeatherClientApiKeyHasNotBeenFoundException extends RuntimeException {
    public WeatherClientApiKeyHasNotBeenFoundException(String message) {
        super(message);
    }

  public WeatherClientApiKeyHasNotBeenFoundException() {
  }
}
