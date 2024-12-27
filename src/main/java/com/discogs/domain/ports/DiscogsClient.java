package com.discogs.domain.ports;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface DiscogsClient {

    ReleaseResponse searchArtistRelease(String discogsId) throws URISyntaxException, MalformedURLException;
}
