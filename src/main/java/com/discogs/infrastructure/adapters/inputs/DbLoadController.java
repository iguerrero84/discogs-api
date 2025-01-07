package com.discogs.infrastructure.adapters.inputs;

import com.discogs.application.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/demo")
public class DbLoadController {

    private final ArtistService artistService;

    @Autowired
    public DbLoadController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping("/initDBLoad")
    public void initDatabaseLoad() throws Exception {
        artistService.initDatabaseLoadFromDisgosApi();
    }
}
