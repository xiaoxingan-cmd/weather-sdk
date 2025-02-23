package com.weatherSDK;

import com.weatherSDK.api.GeocodingAPI;
import com.weatherSDK.api.WeatherClient;
import com.weatherSDK.api.WeatherOfCityAPI;
import com.weatherSDK.behaviour.BehaviourMode;
import com.weatherSDK.cache.CachedData;
import com.weatherSDK.exceptions.weatherClient.WeatherClientApiKeyDuplicatedException;
import com.weatherSDK.exceptions.weatherClient.WeatherClientApiKeyHasNotBeenFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a basic class to initialize the WeatherSDK.<br/>
 * It checks a Map of records of API_KEYS that you provide to manage with several instances.<br/>
 * Use instances to set up multiple profiles to handle with.<br/><br/>
 * You have to use static getInstance() method to start work with the WeatherSDK.
 */

@Slf4j
public class WeatherSDKClientHandler {
    private static final Map<String, WeatherClient> instances = new HashMap<>();

    /**
     * getInstance() method controls the Map of 'instances' preventing you to create profile with API_KEY that has duplicated.
     * @param apiKey you have to provide the valid API_KEY from OpenWeatherMap.
     * @param behaviourMode behaviourMode is useful to point on work mode you want to work with.
     * @return getInstance() returns the instance of WeatherClient which works with specified API_KEY.
     * @throws WeatherClientApiKeyDuplicatedException throws this exception in case the API_KEY is already presented in the instance pool.
     * @see <a href="https://openweathermap.org/api">OpenWeatherMap</a>
     */
    public static WeatherClient getInstance(String apiKey, BehaviourMode behaviourMode) {
        if (instances.containsKey(apiKey)) {
            throw new WeatherClientApiKeyDuplicatedException("The specified API key already exists in the instance pool");
        }

        WeatherClient weatherClient = new WeatherClient(apiKey, behaviourMode, new CachedData(), new WeatherOfCityAPI(), new GeocodingAPI());
        instances.put(apiKey, weatherClient);
        log.debug("The new instance of the weather client has been successfully created");
        return weatherClient;
    }

    /**
     * Use this method to delete instance with the specified API_KEY.
     * @throws WeatherClientApiKeyHasNotBeenFoundException throws this exception in case the API_KEY does not exist in the instance pool.
     * @param apiKey use the API_KEY you used to create the instance to delete it.
     */
    public static void removeInstance(String apiKey) {
        if (!instances.containsKey(apiKey)) {
            throw new WeatherClientApiKeyHasNotBeenFoundException("Instance with the specified API key does not exist in the instance pool");
        }
        instances.remove(apiKey);
    }
}
