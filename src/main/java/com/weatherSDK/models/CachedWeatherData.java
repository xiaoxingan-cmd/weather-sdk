package com.weatherSDK.models;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * This class describes weather data that have been cached.
 */
@Data
public class CachedWeatherData {
    String cityName;
    String data;
    LocalDateTime lastUpdate;

    public CachedWeatherData(String cityName, String data, LocalDateTime lastUpdate) {
        this.data = data;
        this.lastUpdate = lastUpdate;
    }

    /**
     * This method checks if cached data is still fresh to return.
     * @param minutes time to compare with time of this instance.
     */
    public boolean isFresh(int minutes) {
        if (lastUpdate != null && lastUpdate.isAfter(LocalDateTime.now().minusMinutes(minutes))) {
            return true;
        } else {
            return false;
        }
    }
}
