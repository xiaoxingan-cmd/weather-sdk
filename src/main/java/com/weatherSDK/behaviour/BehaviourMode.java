package com.weatherSDK.behaviour;

/**
 * When you initialize WeatherSDK in the first time you have to provide a Behaviour mode you want to use.<br/>
 * ON_DEMAND - useful if you desire to get data from the API only once.<br/>
 * POLLING - useful if you need to get data from the API several times automatically during the specified time. Note that you have to put some data in cache before Polling mode can interact with it.<br/>
 * To put any data in cache just call getWeatherData(String nameOfCity) method in WeatherClient.
 * @see com.weatherSDK.api.WeatherClient
 */
public enum BehaviourMode {
    ON_DEMAND,
    POLLING,
}
