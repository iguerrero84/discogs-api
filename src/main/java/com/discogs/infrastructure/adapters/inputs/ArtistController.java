package com.discogs.infrastructure.adapters.inputs;

import com.discogs.application.ArtistService;
import com.discogs.domain.dto.ArtistDTO;
import com.discogs.domain.dto.ReleaseResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artists")
    public ResponseEntity<List<ArtistDTO>> getAllArtists() {
        return new ResponseEntity<>(artistService.getAllArtists(), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    @GetMapping("/discogs-release/{id}")
    public ResponseEntity<ReleaseResponseDTO> getDiscogsRelease(@PathVariable String id) {
        try {
            Integer.parseInt(id);
            return new ResponseEntity<>(artistService.getDiscogsRelease(id), HttpStatusCode.valueOf(HttpStatus.OK.value()));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/compare-artists")
    public ResponseEntity<List<Map<String, Object>>> compareArtists(@RequestBody List<Long> artistIds) {
        List<Map<String, Object>> comparisonData = artistService.compareArtists(artistIds);
        return ResponseEntity.ok(comparisonData);
    }
}
