package com.discogs.domain.dto;

import java.util.List;


public record ArtistDTO(

        Long id,

        String name,

        List<ReleaseDTO> releases
) {
}
