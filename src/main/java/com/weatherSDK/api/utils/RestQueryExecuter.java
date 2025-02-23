package com.weatherSDK.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherSDK.behaviour.ServiceType;
import com.weatherSDK.exceptions.api.utils.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * RestQueryExecuter is a class provides Rest Template interface to work with and also checks if response has any mistakes.
 */
public class RestQueryExecuter {
    private RestTemplate restTemplate;
    private String url;
    private ServiceType serviceType;

    public RestQueryExecuter(String url, ServiceType serviceType) {
        this.url = url;
        this.serviceType = serviceType;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Execute your rest template!
     * @return JsonNode that can be processed in the future.
     * @throws JsonProcessingException error during reading the response from Rest Template by JsonNode.
     * @throws RestQueryExecuterResponseIsNullException the REST response is empty or invalid.
     * @throws RestQueryExecutorWrongApiKeyException the API key is invalid or stale.
     */
    public JsonNode execute() throws JsonProcessingException {
        try {
            String response = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            if (jsonNode.isEmpty() || jsonNode.isNull()) {
                throw new RestQueryExecuterResponseIsNullException("The REST response is empty or invalid for type call " + serviceType);
            } else {
                checkForResponseCodeExceptions(jsonNode, serviceType);
                return jsonNode;
            }
        } catch (HttpClientErrorException e) {
            throw new RestQueryExecutorWrongApiKeyException("The API key is invalid or stale");
        }
    }

    /**
     * Depends on the ServiceType it checks for an error code in response. It returns nothing in case everything is fine.
     * @param jsonNode your response is returned by the Rest Template and converted into JsonNode.
     * @param serviceType it tells to method what kind of API service do you use.
     * @throws RestQueryExecutorWrongCityNameException the city name is invalid.
     * @throws RestQueryExecutorTooManyQueriesException to avoid this error, please consider upgrading to a subscription plan that meets your needs or reduce the number of API calls in accordance with the limits.
     * @throws RestQueryExecutorServerReturnedAnException the server returned error codes 500+.
     */
    private void checkForResponseCodeExceptions(JsonNode jsonNode, ServiceType serviceType) {
        if (serviceType == ServiceType.CurrentWeatherType) {
            switch (jsonNode.get("cod").asInt()) {
                case 404:
                    throw new RestQueryExecutorWrongCityNameException("The city name is invalid");

                case 429:
                    throw new RestQueryExecutorTooManyQueriesException("If you have a Free plan of Professional subscriptions and make more than 60 API calls per minute (surpassing the limit of your subscription). To avoid this situation, please consider upgrading to a subscription plan that meets your needs or reduce the number of API calls in accordance with the limits.");

                case 500:
                case 502:
                case 503:
                case 504:
                    throw new RestQueryExecutorServerReturnedAnException("The server returned an error code: " + jsonNode.get("cod").asInt());

                default:
                    break;
            }
        }
    }
}
