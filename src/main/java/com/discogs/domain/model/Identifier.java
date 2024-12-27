package com.discogs.domain.model;

import jakarta.persistence.*;

@Entity
public record Identifier(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
        String type,
        String value,
        String description,

        @ManyToOne
        @JoinColumn(name = "release_id", nullable = false)
        Release release
) {
}