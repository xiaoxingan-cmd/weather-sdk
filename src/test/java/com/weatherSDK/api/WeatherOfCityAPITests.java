package com.weatherSDK.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weatherSDK.exceptions.api.WeatherOfCityResponseIsInvalidException;
import com.weatherSDK.models.CityCoordinates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WeatherOfCityAPITests {
    private WeatherOfCityAPI weatherOfCityAPI;
    String emptyResponse = "Some data";
    String meaningfulResponse = "weather :)";
    String city = "Some city";
    int latitude = 0;
    int longitude = 0;
    String apiKey = "";

    @Test
    public void getWeatherDataWithEmptyResponseTest() {
        weatherOfCityAPI = new WeatherOfCityAPI() {
            @Override
            public String executeRequest(CityCoordinates coordinates, String apiKey) {
                return emptyResponse;
            }
        };

        Assertions.assertThrows(WeatherOfCityResponseIsInvalidException.class, () ->
                weatherOfCityAPI.getWeatherData(new CityCoordinates(city, latitude, longitude), apiKey)
        );
    }

    @Test
    public void getWeatherDataResponseTest() throws JsonProcessingException {
        weatherOfCityAPI = new WeatherOfCityAPI() {
            @Override
            public String executeRequest(CityCoordinates coordinates, String apiKey) {
                return meaningfulResponse;
            }
        };

        Assertions.assertEquals(meaningfulResponse, weatherOfCityAPI.getWeatherData(new CityCoordinates(city, latitude, longitude), apiKey)
        );
    }
}
