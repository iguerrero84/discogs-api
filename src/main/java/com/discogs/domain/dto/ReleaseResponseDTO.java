package com.discogs.domain.dto;

import com.discogs.domain.model.Artist;
import com.discogs.domain.model.Company;
import com.discogs.domain.model.Identifier;
import com.discogs.domain.model.Track;

import java.util.List;

public record ReleaseResponseDTO(
        String id,
        String status,
        String year,
        String resource_url,
        String uri,
        List<Artist> artists,
        String artistsSort,
        List<Company> companies,
        String title,
        String country,
        String released,
        String notes,
        String date_added,
        String date_changed,
        Integer num_for_sale,
        Long lowest_price,
        List<Identifier> identifiers,
        List<String> genres,
        List<String> styles,
        List<Track> trackList
) {
}
