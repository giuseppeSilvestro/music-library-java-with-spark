package com.giuseppeSilvestro.musiclibrary.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;


public class NoPersistentAlbumDAO implements AlbumDAO{
    private List<Album> albums = new ArrayList<>();

    public NoPersistentAlbumDAO() {
        albums.add(new Album("Piano Concerto n.5", "Beethoven", "London", "Rattle", "Giuseppe", "Live"
                ,"1985", "DSD256"));
        albums.add(new Album("Piano Concerto n.5", "Beethoven L.W.", "Berlin", "VonKarajan", "Gennaro", "Live", "1985", "DSD256"));
        albums.add(new Album("Violin Concerto n.6", "Beethoven", "San Carlo", "Verdi", "Rachmaninov", "Studio", "1889", "FLAC"));
        albums.add(new Album("Piano Concerto n.1", "Rachmaninov", "Moscow", "Maviv", "Rachmaninov", "Live", "1923", "WAVE"));
    }

    @Override
    public boolean add(Album album) {
        return albums.add(album);
    }

    @Override
    public List<Album> findAll() {
        return new ArrayList<>(albums);
    }

    @Override
    public Album randomAlbum() {
        Random random = new Random();
        int randomAlbum = random.nextInt(albums.size());
        return albums.get(randomAlbum);
    }

    @Override
    public List<Album> findAlbum(String title, String composer, String orchestra, String director, String mainPerformer, String liveStudio, String year, String quality) {
        List<Album> result = new ArrayList<>();
        for (Album album :
                albums) {
            if (album.getTitle().toLowerCase(Locale.ROOT).contains(title.toLowerCase(Locale.ROOT)) && album.getComposer().toLowerCase(Locale.ROOT).contains(composer.toLowerCase(Locale.ROOT))
                    && album.getDirector().toLowerCase(Locale.ROOT).contains(director.toLowerCase(Locale.ROOT)) && album.getOrchestra().toLowerCase(Locale.ROOT).contains(orchestra.toLowerCase(Locale.ROOT))
                    && album.getMainPerformer().toLowerCase(Locale.ROOT).contains(mainPerformer.toLowerCase(Locale.ROOT)) && album.getLiveStudio().toLowerCase(Locale.ROOT).contains(liveStudio.toLowerCase(Locale.ROOT))
                    && album.getYear().contains(year) && album.getQuality().toLowerCase(Locale.ROOT).contains(quality.toLowerCase(Locale.ROOT))) {
                result.add(album);
            }
        }
        return result;
    }

}
