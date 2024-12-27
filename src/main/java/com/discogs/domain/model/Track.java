package com.discogs.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record Track(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
        String name,
        String anv,
        String role,
        String tracks,
        String resourceUrl
) {
}
