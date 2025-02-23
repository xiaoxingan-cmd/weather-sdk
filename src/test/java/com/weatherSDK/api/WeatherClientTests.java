package com.weatherSDK.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weatherSDK.behaviour.BehaviourMode;
import com.weatherSDK.cache.CachedData;
import com.weatherSDK.models.CachedWeatherData;
import com.weatherSDK.models.CityCoordinates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class WeatherClientTests {
    @Mock
    GeocodingAPI geocodingAPI;
    @Mock
    WeatherOfCityAPI weatherOfCityAPI;
    @Mock
    CachedData cachedData;
    @Mock
    CityCoordinates cityCoordinates;
    @Mock
    CachedWeatherData cachedWeatherData;

    String apiKey = "someApiKey";
    String someCity = "Some City";
    String testJsonData = "Test Json Data";
    BehaviourMode behaviourMode = BehaviourMode.POLLING;
    WeatherClient weatherClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherClient = new WeatherClient(apiKey, behaviourMode, cachedData, weatherOfCityAPI, geocodingAPI);
    }

    @AfterEach
    public void cleanup() {
        Mockito.clearAllCaches();
    }

    @Test
    public void getWeatherDataTest() throws JsonProcessingException {
        Mockito.when(geocodingAPI.getCoordinates(someCity, apiKey)).thenReturn(cityCoordinates);
        Mockito.when(weatherOfCityAPI.getWeatherData(cityCoordinates, apiKey)).thenReturn(testJsonData);
        Mockito.when(cachedData.get(someCity)).thenReturn(Optional.empty());

        Assertions.assertEquals(testJsonData, weatherClient.getWeatherData(someCity));
    }

    @Test
    public void getWeatherDataFromCacheTest() throws JsonProcessingException {
        Mockito.when(cachedData.get(someCity)).thenReturn(Optional.of(cachedWeatherData));
        Mockito.when(cachedWeatherData.getData()).thenReturn(testJsonData);

        Assertions.assertEquals(testJsonData, weatherClient.getWeatherData(someCity));
    }

    @Test
    public void stopPollingTest() {
        WeatherClient weatherClient1 = new WeatherClient("someApiKey", BehaviourMode.POLLING, cachedData, weatherOfCityAPI, geocodingAPI);
        boolean result = weatherClient1.stopPolling();

        Assertions.assertTrue(result);
    }
}
