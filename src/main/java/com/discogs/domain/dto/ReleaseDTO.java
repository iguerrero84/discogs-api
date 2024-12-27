package com.discogs.domain.dto;

public record ReleaseDTO(
        Long id,
        String status,
        String uri,
        String year,
        String title,
        String country,
        String genre
//        String dateAdded,
//        String dateChanged,
//        Integer numForSale,
//        Long lowestPrice,
//        String released,
//        String releasedFormatted,
//        String resourceUrl,

) {
}
