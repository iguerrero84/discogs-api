package com.discogs.domain.ports;

import com.discogs.domain.dto.ReleaseResponseDTO;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface DiscogsClient {

    ReleaseResponseDTO searchArtistRelease(String discogsId) throws URISyntaxException, MalformedURLException;
}
