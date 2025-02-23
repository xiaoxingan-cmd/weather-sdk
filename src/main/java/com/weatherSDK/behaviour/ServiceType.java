package com.weatherSDK.behaviour;

/**
 * ServiceType points what API is used when RestTemplateExecuter is called to work with response codes.
 * @see <a href="https://openweathermap.org/api/geocoding-api">GeocoderType</a>
 * @see <a href="https://openweathermap.org/current">CurrentWeatherType</a>
 * @see com.weatherSDK.api.utils.RestQueryExecuter
 */
public enum ServiceType {
    GeocoderType,
    CurrentWeatherType,
}
