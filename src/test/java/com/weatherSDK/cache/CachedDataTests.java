package com.weatherSDK.cache;

import com.weatherSDK.models.CachedWeatherData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CachedDataTests {
    @Mock
    CachedWeatherData cachedWeatherData;

    CachedData cachedData = new CachedData();


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void cleanup() {
        Mockito.clearAllCaches();
    }

    @Test
    public void removeEldestEntryTest() {
        for (int i = 0; i < 11; i++) {
            cachedData.put("element" + i, cachedWeatherData);
        }

        Assertions.assertEquals(Optional.empty(), cachedData.get("element11"));
    }

    @Test
    public void getCachedDataWhenItsExpiredTest() {
        cachedData.put("expiredElement", cachedWeatherData);

        Mockito.when(cachedWeatherData.isFresh(10)).thenReturn(false);
        Assertions.assertEquals(Optional.empty(), cachedData.get("expiredElement"));
    }

    @Test
    public void getCachedDataWhenItsNotExpiredTest() {
        cachedData.put("notExpiredElement", cachedWeatherData);

         Mockito.when(cachedWeatherData.isFresh(10)).thenReturn(true);
         Assertions.assertEquals(cachedWeatherData, cachedData.get("notExpiredElement").get());
    }

    @Test
    public void getCachedDataSetTest() {
        cachedData.put("setElement", cachedWeatherData);
        Set<String> stringSet = new HashSet<>();
        stringSet.add("setElement");

        Assertions.assertEquals(stringSet, cachedData.getNamesOfCities());
    }
}
