package com.discogs.infrastructure.adapters.api;

import com.discogs.domain.ports.DiscogsClient;
import com.discogs.domain.ports.ReleaseResponse;
import com.discogs.infrastructure.configuration.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


@Component
public class DiscogsClientImpl implements DiscogsClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscogsClientImpl.class);

    private static final String DISCOGS_URL = "https://api.discogs.com/releases/";

    private final RestTemplate template = new RestTemplate();

    @Override
    public ReleaseResponse searchArtistRelease(String discogsId) throws MalformedURLException, URISyntaxException {
        URL url = null;
        try {
            url = new URL(DISCOGS_URL + discogsId);
        } catch (MalformedURLException e) {
            LOGGER.error("There was an error with DISCOGS_URL in searchArtistRelease");
            throw new MalformedURLException();
        }

        return template.getForObject(url.toURI(), ReleaseResponse.class);

    }
}
