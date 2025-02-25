# WeatherDataSDK
## Introduction
> WeatherDataSDK is a lightweight and easy-to-use SDK designed to seamlessly integrate weather data collection into your applications. The SDK returns JSON weather data for any specified city. With WeatherDataSDK, developers can also use polling methods to automatically retrieve weather information at regular intervals, making it an ideal solution for applications that require up-to-date weather conditions. Whether you're building a weather app, a travel planner, or any application that relies on weather data, WeatherDataSDK will handle it!

## Contents
> * [Introduction](https://github.com/xiaoxingan-cmd/weather-sdk#introduction)
> * [Prerequisites](https://github.com/xiaoxingan-cmd/weather-sdk#prerequisites)
> * [Installation](https://github.com/xiaoxingan-cmd/weather-sdk#installation)
> * [Example of Usage](https://github.com/xiaoxingan-cmd/weather-sdk#example-of-usage)
> * [Expected Result](https://github.com/xiaoxingan-cmd/weather-sdk#expected-result)
> * [Other](https://github.com/xiaoxingan-cmd/weather-sdk#other)

## Prerequisites
You need to get your own API key to work with this SDK. This one works with [OpenWeather](https://openweathermap.org/api) API.

## Installation
To get started, add the following dependency to your pom.xml file.
```xml
<dependency>
    <groupId>io.github.xiaoxingan-cmd</groupId>
    <artifactId>WeatherDataSDK</artifactId>
    <version>1.0.0</version>
</dependency>
```
> [!WARNING]
> Make sure to re-sync Maven changes after updating your pom.xml file.

## Example of Usage
1. Initialize SDK client and pass your API key and desired behaviour.
2. Use WeatherClient's method to get data for the city you provide.
```java
import com.fasterxml.jackson.core.JsonProcessingException;
import com.weatherSDK.WeatherSDKClientHandler;
import com.weatherSDK.api.WeatherClient;
import com.weatherSDK.behaviour.BehaviourMode;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        WeatherClient weatherClient = WeatherSDKClientHandler.getInstance("your api key", BehaviourMode.ON_DEMAND);
        System.out.println(weatherClient.getWeatherData("London"));
    }
}
```
> [!TIP]
> If you don't fully understand what an instance means, think of it as something that allows you to work with multiple API keys at the same time.

## Expected Result
>[!NOTE]
> The SDK returns JSON as a string in a pretty-printed format. </br>
> To parse it, you can use libraries like Jackson.
```
{
  "coord" : {
    "lon" : -0.1276,
    "lat" : 51.5073
  },
  "weather" : [ {
    "id" : 800,
    "main" : "Clear",
    "description" : "clear sky",
    "icon" : "01n"
  } ],
  "base" : "stations",
  "main" : {
    "temp" : 279.99,
    "feels_like" : 279.4,
    "temp_min" : 278.44,
    "temp_max" : 281.51,
    "pressure" : 1016,
    "humidity" : 80,
    "sea_level" : 1016,
    "grnd_level" : 1012
  },
  "visibility" : 10000,
  "wind" : {
    "speed" : 1.34,
    "deg" : 294,
    "gust" : 3.13
  },
  "clouds" : {
    "all" : 3
  },
  "dt" : 1740513240,
  "sys" : {
    "type" : 2,
    "id" : 2091269,
    "country" : "GB",
    "sunrise" : 1740466458,
    "sunset" : 1740504790
  },
  "timezone" : 0,
  "id" : 2643743,
  "name" : "London",
  "cod" : 200
}
```
## Other
* [Javadoc Documentation](https://github.com/xiaoxingan-cmd/weather-sdk/tree/master/docs/javadoc)
