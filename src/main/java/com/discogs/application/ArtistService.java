package com.discogs.application;

import com.discogs.domain.dto.ArtistDTO;
import com.discogs.domain.dto.ReleaseDTO;
import com.discogs.domain.model.Artist;
import com.discogs.domain.model.Release;
import com.discogs.domain.ports.ArtistRepository;
import com.discogs.domain.ports.DiscogsClient;
import com.discogs.domain.ports.ReleaseResponse;
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
    private final ArtistRepository artistRepository;
    private final DiscogsClient discogsClient;

    private static final String RELEASE_YEAR = "releaseYear";

    public ArtistService(ArtistRepository artistRepository, DiscogsClient discogsClient) {
        this.artistRepository = artistRepository;
        this.discogsClient = discogsClient;
    }


    public void initDatabaseLoadFromDisgosApi() throws Exception {
        startDemoDatabaseLoader();
    }

    public ReleaseResponse getDiscogsRelease(String id) {
        try {
            return discogsClient.searchArtistRelease(id);
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
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

            artistData.put("commonGenres", commonGenres);

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
