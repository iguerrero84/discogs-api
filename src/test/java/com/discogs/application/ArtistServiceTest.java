package com.discogs.application;

import com.discogs.domain.model.Artist;
import com.discogs.domain.model.Release;
import com.discogs.infrastructure.repository.ArtistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @InjectMocks
    private ArtistService artistService;

    @Mock
    private ArtistRepository artistRepository;

    @Test
    void testCompareArtists() {

        List<Long> artistIds = List.of(1L, 2L);
        when(artistRepository.findByDiscogsIds(artistIds)).thenReturn(getListOfArtists());

        // Call the method
        List<Map<String, Object>> result = artistService.compareArtists(artistIds);

        // Assert the results
        assertEquals(2, result.size());

        // Check the first artist data
        Map<String, Object> artist1Data = result.get(0);
        assertEquals("Artist One", artist1Data.get("artistName"));
        assertEquals(2, artist1Data.get("numReleases"));
        assertEquals("1985, 1990", artist1Data.get("releaseYear"));
        assertEquals("Rock, Jazz", artist1Data.get("commonGenres"));
        assertEquals("Album OneAlbum Two", artist1Data.get("albumTitle"));
        //assertEquals("USA, Canada", artist1Data.get("country"));

        // Check the second artist data
        Map<String, Object> artist2Data = result.get(1);
        assertEquals("Artist Two", artist2Data.get("artistName"));
        assertEquals(1, artist2Data.get("numReleases"));
        assertEquals("1995", artist2Data.get("releaseYear"));
        assertEquals("Pop", artist2Data.get("commonGenres"));
        assertEquals("Album Three", artist2Data.get("albumTitle"));
        assertEquals("UK", artist2Data.get("country"));

        // Verify the repository call
        verify(artistRepository, times(1)).findByDiscogsIds(artistIds);
    }


    private List<Artist> getListOfArtists(){
        // Mock data
        Release release1 = new Release();
        release1.setTitle("Album One");
        release1.setCountry("US");
        release1.setGenre("Rock");
        release1.setYear("1985");

        Release release2 = new Release();
        release2.setTitle("Album Two");
        release2.setCountry("Canada");
        release2.setGenre("Jazz");
        release2.setYear("1990");

        Release release3 = new Release();
        release3.setTitle("Album Three");
        release3.setCountry("UK");
        release3.setGenre("Pop");
        release3.setYear("1995");

        Artist artist1 = new Artist();
        artist1.setId(1L);
        artist1.setName("Artist One");
        artist1.setReleases(List.of(release1, release2));

        Artist artist2 = new Artist();
        artist2.setId(2L);
        artist2.setName("Artist Two");
        artist2.setReleases(List.of(release3));

        return List.of(artist1, artist2);
    }
}
