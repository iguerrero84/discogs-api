package com.discogs.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record Company(
        @Id Long id,
        String name,
        String catNo,
        String entityType,
        String entityTypeName,
        String resourceUrl
) {
}