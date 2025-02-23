package com.weatherSDK.cache;

import com.weatherSDK.models.CachedWeatherData;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * This class represents all data that have been cached.<br/>
 * It has max cache capacity of 10. If pool of cache will be overwhelmed it will start to delete older elements.<br/>
 * Before returning any object from cache pool it always check if data is still fresh. The data becomes stale if it older than 10 minutes.<br/>
 * This one is handled by the WeatherClient class.
 * @see com.weatherSDK.api.WeatherClient
 */
@Slf4j
public class CachedData {
    private static final int CACHE_LIFETIME_IN_MINUTES = 10;
    private static final int MAX_CACHE_SIZE = 10;

    /**
     * This one is useful to delete older elements in case of cache pool overwhelming.
     */
    private final Map<String, CachedWeatherData> cachePool = new LinkedHashMap<>(MAX_CACHE_SIZE, 1.0f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, CachedWeatherData> eldest) {
            return size() > MAX_CACHE_SIZE;
        }
    };

    /**
     * Insert a new data to the cache pool.
     */
    public void put(String nameOfCity, CachedWeatherData cachedData) {
        log.debug("Putting cached data for city {}", nameOfCity);
        cachePool.put(nameOfCity, cachedData);
    }

    /**
     * Get a cached data from the cache pool.<br/>
     * Note this method returns Optional.
     */
    public Optional<CachedWeatherData> get(String nameOfCity) {
        CachedWeatherData data = cachePool.get(nameOfCity);

        if (data == null) {
            log.debug("No cached data for city {}", nameOfCity);
            return Optional.empty();
        }

        if (data.isFresh(CACHE_LIFETIME_IN_MINUTES)) {
            log.debug("Cached data for city {} has been found", nameOfCity);
            return Optional.of(data);
        } else {
            log.debug("Cached data for city {} has expired. The cache record will be removed", nameOfCity);
            cachePool.remove(nameOfCity);
            return Optional.empty();
        }
    }

    /**
     * The WeatherClient uses this one while operating under BehaviourMode.Polling to get names of all saved cities in cache pool.
     */
    public Set<String> getNamesOfCities() {
        return cachePool.keySet();
    }
}
