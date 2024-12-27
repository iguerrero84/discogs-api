package com.discogs.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record Image(
        @Id Long id,
        String type,
        String uri,
        String resourceUrl,
        Integer width,
        Integer height
) {
}
