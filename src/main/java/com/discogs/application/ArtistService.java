package com.discogs.application;

import com.discogs.domain.dto.ArtistDTO;
import com.discogs.domain.dto.ReleaseDTO;
import com.discogs.domain.model.Artist;
import com.discogs.domain.model.Release;
import com.discogs.infrastructure.repository.ArtistRepository;
import com.discogs.domain.ports.DiscogsClient;
import com.discogs.domain.dto.ReleaseResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.discogs.infrastructure.configuration.AppDemoDBLoader.startDemoDatabaseLoader;

@Service
public class ArtistService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistService.class);

    private final ArtistRepository artistRepository;
    private final DiscogsClient discogsClient;

    private static final String RELEASE_YEAR = "releaseYear";

    public ArtistService(ArtistRepository artistRepository, DiscogsClient discogsClient) {
        this.artistRepository = artistRepository;
        this.discogsClient = discogsClient;
    }


    public void initDatabaseLoadFromDisgosApi() {
        startDemoDatabaseLoader();
    }

    public ReleaseResponseDTO getDiscogsRelease(String id) {
        ReleaseResponseDTO result = null;
        try {
            result = discogsClient.searchArtistRelease(id);
        } catch (MalformedURLException | URISyntaxException e ) {
            LOGGER.error("There was an error getting Discogs releases");
        }
        return result;
    }

    public List<ArtistDTO> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(artist -> new ArtistDTO(
                        artist.getId(),
                        artist.getName(),
                        artist.getReleases().stream()
                                .map(release -> new ReleaseDTO(
                                        release.getId(),
                                        release.getStatus(),
                                        release.getUri(),
                                        release.getYear(),
                                        release.getTitle(),
                                        release.getCountry(),
                                        release.getGenre()
                                )).toList()
                )).toList();
    }

    public List<Map<String, Object>> compareArtists(List<Long> artistIds) {

        List<Artist> artists = artistRepository.findByDiscogsIds(artistIds);

        return artists.stream().map(artist -> {
            Map<String, Object> artistData = new HashMap<>();
            artistData.put("artistName", artist.getName());
            artistData.put("numReleases", artist.getReleases().size());

            // Sort releases by release year (from oldest to youngest)
            List<Release> sortedReleases = artist.getReleases().stream()
                    .sorted(Comparator.comparing(Release::getYear))  // Assuming Release has a getReleaseDate method
                    .toList();

            // Map to release years in chronological order
            String releaseYear = sortedReleases.stream()
                    .map(release -> String.valueOf(release.getYear()))  // Extract the year from release date
                    .collect(Collectors.joining(", ")); // Join years with comma separation

            artistData.put(RELEASE_YEAR, releaseYear);

            // Collect common genres (this example joins all genres for the artist; you might want to process it differently)
            String commonGenres = artist.getReleases().stream()
                    .map(Release::getGenre)
                    .collect(Collectors.joining(", "));  // Join genres with comma separation

            String country = artist.getReleases().stream()
                            .map(Release::getCountry)
                                    .collect(Collectors.joining());

            String albumTitle = artist.getReleases().stream()
                    .map(Release::getTitle)
                    .collect(Collectors.joining());

            artistData.put("commonGenres", commonGenres);
            artistData.put("albumTitle", albumTitle);
            artistData.put("country", country);

            return artistData;
        }).sorted((a, b) -> {
            String yearA = (String) a.get(RELEASE_YEAR);
            String yearB = (String) b.get(RELEASE_YEAR);

            // Extract the first year and compare
            int yearAInt = Integer.parseInt(yearA.split(",")[0].trim());
            int yearBInt = Integer.parseInt(yearB.split(",")[0].trim());

            return Integer.compare(yearAInt, yearBInt);
        }).toList();
    }


}
