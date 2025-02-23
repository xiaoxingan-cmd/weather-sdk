package com.weatherSDK.api.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RestQueryTests {
    @Test
    public void RestQueryBuilderTest() {
        String testApiKey = "apiKey";
        String nameOfCity = "London";
        int limit = 1;
        String GEOCODING_BASE_URL = "https://api.openweathermap.org/geo/1.0/direct";

        String expectedResult = "https://api.openweathermap.org/geo/1.0/direct?q=" + nameOfCity + "&limit=" + limit + "&appid=" + testApiKey;


        Assertions.assertEquals(expectedResult, new RestQuery.Builder(GEOCODING_BASE_URL, testApiKey)
                .addParameter("q", nameOfCity)
                .addParameter("limit", limit)
                .build().getUrl());
    }
}
