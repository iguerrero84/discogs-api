package com.discogs.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record ExtraArtist(
        @Id Long id,
        String name,
        String anv,
        String role,
        String tracks,
        String resourceUrl
) {
}
