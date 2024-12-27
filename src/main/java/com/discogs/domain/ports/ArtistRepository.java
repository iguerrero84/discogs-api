package com.discogs.domain.ports;

import com.discogs.domain.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query("SELECT a FROM Artist a WHERE a.discogs_id IN :discogsIds")
    List<Artist> findByDiscogsIds(@Param("discogsIds") List<Long> discogsIds);
}
