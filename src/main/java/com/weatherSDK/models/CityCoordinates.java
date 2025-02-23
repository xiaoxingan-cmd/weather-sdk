package com.weatherSDK.models;

import lombok.Data;

/**
 * This class contains data about city coordinates providing name of city, latitude and longitude.
 */
@Data
public class CityCoordinates {
    private String cityName;
    private double latitude;
    private double longitude;

    public CityCoordinates(String cityName, double latitude, double longitude) {
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
