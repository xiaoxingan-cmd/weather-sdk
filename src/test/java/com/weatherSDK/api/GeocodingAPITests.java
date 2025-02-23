package com.weatherSDK.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherSDK.exceptions.api.GeocodingApiCoordinatesAreNullException;
import com.weatherSDK.models.CityCoordinates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class GeocodingAPITests {
    private GeocodingAPI geocodingAPI;
    @Mock
    JsonNode jsonNode;

    @BeforeEach
    void setUp() {
        geocodingAPI = new GeocodingAPI() {
            @Override
            public JsonNode executeRequest(String cityName, String apiKey) {
                return jsonNode;
            }
        };

        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void cleanup() {
        Mockito.clearAllCaches();
    }

    @Test
    void getCoordinatesWithNullResponseTest() {
        Assertions.assertThrows(GeocodingApiCoordinatesAreNullException.class, () ->
            geocodingAPI.getCoordinates("London", "apiKey")
        );
    }

    @Test
    void getCoordinatesTest() throws JsonProcessingException {
        String jsonString = "{ \"lat\": 40.7128, \"lon\": -74.0060 }";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeTest = objectMapper.readTree(jsonString);

        Mockito.when(jsonNode.get(0)).thenReturn(jsonNodeTest);
        Assertions.assertEquals(new CityCoordinates("London", 40.7128, -74.0060), geocodingAPI.getCoordinates("London", "apiKey"));
    }

    @Test
    void getCoordinatesWithoutLatitudeTest() throws JsonProcessingException {
        String jsonString = "{ \"lon\": -74.0060 }";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeTest = objectMapper.readTree(jsonString);

        Mockito.when(jsonNode.get(0)).thenReturn(jsonNodeTest);
        Assertions.assertThrows(GeocodingApiCoordinatesAreNullException.class, () -> geocodingAPI.getCoordinates("London", "apiKey"));
    }

    @Test
    void getCoordinatesWithoutLongtitudeTest() throws JsonProcessingException {
        String jsonString = "{ \"lat\": 40.7128 }";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNodeTest = objectMapper.readTree(jsonString);

        Mockito.when(jsonNode.get(0)).thenReturn(jsonNodeTest);
        Assertions.assertThrows(GeocodingApiCoordinatesAreNullException.class, () -> geocodingAPI.getCoordinates("London", "apiKey"));
    }
}
