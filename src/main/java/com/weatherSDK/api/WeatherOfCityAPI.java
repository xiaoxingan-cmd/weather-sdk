package com.weatherSDK.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weatherSDK.api.utils.RestQuery;
import com.weatherSDK.api.utils.RestQueryExecuter;
import com.weatherSDK.behaviour.ServiceType;
import com.weatherSDK.exceptions.api.WeatherOfCityResponseIsInvalidException;
import com.weatherSDK.models.CityCoordinates;
import lombok.extern.slf4j.Slf4j;

/**
 * The WeatherOfCityAPI uses coordinates from the GeocodingAPI to grab a fresh data for city you provide. Check the WeatherClient's methods to know more.
 * @see <a href="https://openweathermap.org/current">WeatherOfCityAPI</a>
 * @see <a href="https://openweathermap.org/api/geocoding-api">GeocodingAPI</a>
 */
@Slf4j
public class WeatherOfCityAPI {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    /**
     * Returns JSON data about a current weather for the specified name of city.
     * @param coordinates provide coordinates of your city.
     * @param apiKey provide your API_KEY from the OpenWeather.
     * @return Is actual result!
     * @throws JsonProcessingException error during reading the response from Rest Template by JsonNode.
     * @throws WeatherOfCityResponseIsInvalidException invalid response from the OpenWeatherMap API.
     */
    public String getWeatherData(CityCoordinates coordinates, String apiKey) throws JsonProcessingException {
        String response = executeRequest(coordinates, apiKey);
        log.debug("Query for weather data in {} has been executed: {}", coordinates.getCityName(), response);

        if (response.contains("weather")) {
            return response;
        } else {
            throw new WeatherOfCityResponseIsInvalidException("Invalid response from OpenWeatherMap API: " + response);
        }
    }

    /**
     * Execute your rest template!
     * @param coordinates provide coordinates of your city.
     * @param apiKey provide your API_KEY from the OpenWeather.
     * @return Is actual result!
     * @throws JsonProcessingException error during reading the response from Rest Template by JsonNode.
     */
    protected String executeRequest(CityCoordinates coordinates, String apiKey) throws JsonProcessingException {
        return new RestQueryExecuter(new RestQuery.Builder(BASE_URL, apiKey)
                .addParameter("lat", coordinates.getLatitude())
                .addParameter("lon", coordinates.getLongitude())
                .build().getUrl(), ServiceType.CurrentWeatherType).execute().toPrettyString();
    }
}
