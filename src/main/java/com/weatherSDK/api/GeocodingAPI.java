package com.weatherSDK.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.weatherSDK.api.utils.RestQuery;
import com.weatherSDK.api.utils.RestQueryExecuter;
import com.weatherSDK.behaviour.ServiceType;
import com.weatherSDK.exceptions.api.GeocodingApiCoordinatesAreNullException;
import com.weatherSDK.models.CityCoordinates;
import lombok.extern.slf4j.Slf4j;

/**
 * The GeocodingAPI is required to collect coordinates for the WeatherOfCityAPI. Check the WeatherClient's methods to know more.
 * @see <a href="https://openweathermap.org/api/geocoding-api">GeocodingAPI</a>
 * @see <a href="https://openweathermap.org/current">WeatherOfCityAPI</a>
 */
@Slf4j
public class GeocodingAPI {
    private final String BASE_URL = "https://api.openweathermap.org/geo/1.0/direct";

    /**
     * Returns coordinates for the specified name of city.
     * @param cityName have to be provided to get coordinates from the API.
     * @param apiKey provide your API_KEY from the OpenWeather.
     * @return CityCoordinates class to represent coordinates.
     * @throws JsonProcessingException error during reading the response from Rest Template by JsonNode.
     * @throws GeocodingApiCoordinatesAreNullException missing latitude or longitude in response for the specified city.
     */
    public CityCoordinates getCoordinates(String cityName, String apiKey) throws JsonProcessingException {
        JsonNode jsonNode = executeRequest(cityName, apiKey);
        log.debug("Geocoding query for {} has been executed", cityName);

        JsonNode firstNode = jsonNode.get(0);

        if (firstNode == null || !firstNode.has("lat") || !firstNode.has("lon")) {
            throw new GeocodingApiCoordinatesAreNullException("Missing latitude or longitude in response for city: " + cityName);
        }

        double latitude = firstNode.get("lat").asDouble();
        double longitude = firstNode.get("lon").asDouble();

        log.debug("Geocoding latitude {} longitude {} for {}", latitude, longitude, cityName);
        return new CityCoordinates(cityName, latitude, longitude);
    }

    /**
     * Execute your rest template!
     * @param cityName have to be provided to get coordinates from the API.
     * @param apiKey provide your API_KEY from the OpenWeather.
     * @return JsonNode that can be processed in the future.
     * @throws JsonProcessingException error during reading the response from Rest Template by JsonNode.
     */
    protected JsonNode executeRequest(String cityName, String apiKey) throws JsonProcessingException {
        return new RestQueryExecuter(new RestQuery.Builder(BASE_URL, apiKey)
                .addParameter("q", cityName)
                .addParameter("limit", 1)
                .build().getUrl(), ServiceType.GeocoderType).execute();
    }
}
