package com.weatherSDK.api.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * This is a simple RestQueryBuilder. It uses Builder Pattern to work.
 * @see <a href="https://refactoring.guru/design-patterns/builder">Builder Pattern</a>
 */
public class RestQuery {
    private final String baseUrl;
    private final String apiKey;
    private final Map<String, String> parameters;

    private RestQuery(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.apiKey = builder.apiKey;
        this.parameters = builder.parameters;
    }

    public String getUrl() {
        StringJoiner joiner = new StringJoiner("&", "?", "");
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            joiner.add(entry.getKey() + "=" + entry.getValue());
        }
        joiner.add("appid=" + apiKey);
        return baseUrl + joiner;
    }

    public static class Builder {
        private final String baseUrl;
        private final String apiKey;
        private final Map<String, String> parameters = new HashMap<>();

        public Builder(String baseUrl, String apiKey) {
            this.baseUrl = baseUrl;
            this.apiKey = apiKey;
        }

        public Builder addParameter(String key, Object value) {
            parameters.put(key, String.valueOf(value));
            return this;
        }

        public RestQuery build() {
            return new RestQuery(this);
        }
    }
}
