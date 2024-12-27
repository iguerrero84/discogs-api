package com.discogs.infrastructure.configuration;

import com.discogs.domain.model.Artist;
import com.discogs.domain.model.Release;
import com.discogs.domain.ports.ReleaseResponse;
import com.discogs.infrastructure.adapters.api.DiscogsClientImpl;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppDemoDBLoader {

    private static final DiscogsClientImpl discogsClient = new DiscogsClientImpl();

    public static void startDemoDatabaseLoader() throws URISyntaxException, MalformedURLException {

        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        for (int i = 0; i <= 10; i++) {
            String discogsId = "24950" + i;
            ReleaseResponse r = discogsClient.searchArtistRelease(discogsId);

            Artist artist = new Artist();
            artist.setDiscogsId(discogsId);
            artist.setName(r.artists().get(0).getName());

            Release release = new Release();
            release.setStatus(r.status());
            release.setUri(r.uri());
            release.setYear(r.year());
            release.setTitle(r.title());
            release.setCountry(r.country());
            release.setDateAdded(r.date_added());
            release.setDateChanged(r.date_changed());
            release.setLowestPrice(r.lowest_price());
            release.setNumForSale(r.num_for_sale());
            release.setResourceUrl(r.resource_url());
            release.setGenre(r.genres().stream().collect(Collectors.joining(", ")));
            release.setArtist(artist);

            List<Release> releaseList = new ArrayList<>();
            releaseList.add(release);

            artist.setReleases(releaseList);

            session.save(artist);
        }
        session.getTransaction().commit();
    }

    private AppDemoDBLoader() {}
}
