package com.discogs.domain.model;

import jakarta.persistence.*;

@Entity
public record Video(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
        String uri,
        String title,
        String description,
        Integer duration,
        boolean embed,

        @ManyToOne
        @JoinColumn(name = "release_id", nullable = false)
        Release release
) {
}
