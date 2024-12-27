package com.discogs.infrastructure.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class AppConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    @Bean(name = "appRestClient")
    public RestTemplate getRestClient() {
        RestTemplate restClient = new RestTemplate(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        // Add one interceptor like in your example, except using anonymous class.
        restClient.setInterceptors(Collections.singletonList((request, body, execution) -> {

            LOGGER.debug("Intercepting...");
            return execution.execute(request, body);
        }));

        return restClient;
    }
}
