package com.discogs.infrastructure.adapters.inputs;

import com.discogs.application.ArtistService;
import com.discogs.domain.dto.ArtistDTO;
import com.discogs.domain.dto.ReleaseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistControllerTest {

    @InjectMocks
    private ArtistController artistController;

    @Mock
    private ArtistService artistService;


    @Test
    void testGetArtists_ReturnsArtists() {

        var releaseMadonna = List.of(new ReleaseDTO(1L, "new", "", "1998", "Frozen", "UK", "Electronic"));
        var releaseNiceNice = List.of(new ReleaseDTO(1L, "new", "", "2003", "Chrome", "US", "Rock"));
        var releaseVarious = List.of(new ReleaseDTO(1L, "new", "", "2003", "Demulcent Sessions Volume 1", "US", "Electronic"));

        List<ArtistDTO> mockResponse = Arrays.asList(
                new ArtistDTO(1L, "Madonna", releaseMadonna),
                new ArtistDTO(2L, "Electronic, Rock", releaseNiceNice),
                new ArtistDTO(3L, "Electronic", releaseVarious)
        );

        when(artistService.getAllArtists()).thenReturn(mockResponse);

        ResponseEntity<List<ArtistDTO>> result = artistController.getAllArtists();

        assertThat(result.getStatusCode().is2xxSuccessful());
        assertThat(result.getBody().size() == 3);
    }
}
