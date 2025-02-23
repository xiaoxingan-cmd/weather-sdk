package com.weatherSDK.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weatherSDK.behaviour.BehaviourMode;
import com.weatherSDK.cache.CachedData;
import com.weatherSDK.models.CachedWeatherData;
import com.weatherSDK.models.CityCoordinates;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WeatherClient is a main class which uses all methods together to transform your city's name to the meaningful information.<br/>
 * It also contains methods to handle with POLLING and Cached Data. This class behaviour depends on BehaviourMode was provided on initialization.
 * @see com.weatherSDK.WeatherSDKClientHandler#getInstance(String, BehaviourMode)
 * @see BehaviourMode
 * @see CachedData
 */
@Slf4j
public class WeatherClient {
    private final String apiKey;
    private final BehaviourMode behaviourMode;
    private final CachedData cachedData;
    private final WeatherOfCityAPI weatherOfCityAPI;
    private final GeocodingAPI geocodingAPI;
    private final ExecutorService executorService;
    private Timer pollingTimer;
    public static int pollingPeriod = 5 * 60 * 1000;

    public WeatherClient(String apiKey, BehaviourMode behaviourMode, CachedData cachedData, WeatherOfCityAPI weatherOfCityAPI, GeocodingAPI geocodingAPI) {
        this.apiKey = apiKey;
        this.behaviourMode = behaviourMode;
        this.cachedData = cachedData;
        this.weatherOfCityAPI = weatherOfCityAPI;
        this.geocodingAPI = geocodingAPI;
        this.executorService = Executors.newSingleThreadExecutor();

        if (this.behaviourMode.equals(BehaviourMode.POLLING)) {
            startPolling();
        }
    }

    /**
     * This method receives nameOfCity as a parameter, check cache and return the corresponding value.<br/>
     * On the other hand if the cache does not have any suitable data to return the WeatherClient will trigger GeocodingAPI and WeatherOfCityAPI to collect fresh data.
     * @param nameOfCity have to be provided.
     * @return Returns JSON data.
     * @throws JsonProcessingException error during reading the response from Rest Template by JsonNode.
     */
    public String getWeatherData(String nameOfCity) throws JsonProcessingException {
        Optional<CachedWeatherData> cachedWeatherData = cachedData.get(nameOfCity);

        if (cachedWeatherData.isEmpty()) {
            log.debug("Cached weather data for {} has not been found. Has started to collect fresh one", nameOfCity);
            CityCoordinates coordinates = getCoordinates(nameOfCity);
            String jsonData = weatherOfCityAPI.getWeatherData(coordinates, apiKey);
            cachedData.put(nameOfCity, new CachedWeatherData(nameOfCity, jsonData, LocalDateTime.now()));
            return jsonData;
        } else {
            return cachedWeatherData.get().getData();
        }
    }

    /**
     * This one collects coordinates for required city.
     * @param nameOfCity have to be provided.
     * @return CityCoordinates class to represent coordinates.
     * @throws JsonProcessingException error during reading the response from Rest Template by JsonNode.
     */
    private CityCoordinates getCoordinates(String nameOfCity) throws JsonProcessingException {
        return geocodingAPI.getCoordinates(nameOfCity, apiKey);
    }

    /**
     * The Polling method triggers when you initialize the WeatherClient with BehaviourMode.Polling parameter.<br/>
     * Its automatically refresh data in the cache pool every X minutes. You can set this period using static variable 'pollingPeriod'.<br/>
     * @see <a href="https://medium.com/@sankalpa115/what-is-polling-b1ff70e87001">What is polling?</a>
     */
    private void startPolling() {
        log.info("Starting polling");
        pollingTimer = new Timer(true);
        pollingTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                executorService.submit(() -> {
                    log.info("Refreshing data for all saved cities");
                    cachedData.getNamesOfCities().forEach(city -> {
                        try {
                            CachedWeatherData newData = new CachedWeatherData(city, getWeatherData(city), LocalDateTime.now());
                            cachedData.put(city, newData);
                        } catch (Exception e) {
                            log.error("Error while refreshing data for city {} due polling", city, e);
                        }
                    });
                });
            }
        }, 0, pollingPeriod);
    }

    /**
     * Stop the polling method.
     * @return True Or False.
     */
    public boolean stopPolling() {
        if (pollingTimer != null) {
            pollingTimer.cancel();
            executorService.shutdown();
        }
        log.info("Polling has been stopped");
        return true;
    }
}
