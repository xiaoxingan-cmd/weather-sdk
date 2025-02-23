package com.weatherSDK;

import com.weatherSDK.behaviour.BehaviourMode;
import com.weatherSDK.exceptions.weatherClient.WeatherClientApiKeyDuplicatedException;
import com.weatherSDK.exceptions.weatherClient.WeatherClientApiKeyHasNotBeenFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeatherSDKClientHandlerTests {
    private String apiKeyTest = "something";

    @AfterEach
    void init() throws Exception {
        Field field = WeatherSDKClientHandler.class.getDeclaredField("instances");
        field.setAccessible(true);
        ((Map<?, ?>) field.get(null)).clear();
    }

    @Test
    public void getInstanceWithDuplicateKeyTest(){
        WeatherSDKClientHandler.getInstance(apiKeyTest, BehaviourMode.ON_DEMAND);
        assertThrows(WeatherClientApiKeyDuplicatedException.class, () -> WeatherSDKClientHandler.getInstance(apiKeyTest, BehaviourMode.ON_DEMAND));
    }

    @Test
    public void removeInstanceWithWrongKeyTest() {
        assertThrows(WeatherClientApiKeyHasNotBeenFoundException.class, () -> WeatherSDKClientHandler.removeInstance(apiKeyTest));
    }
}
