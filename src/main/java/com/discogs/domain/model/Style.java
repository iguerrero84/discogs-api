package com.discogs.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record Style(
        @Id Long id,
        String name
) {
}
