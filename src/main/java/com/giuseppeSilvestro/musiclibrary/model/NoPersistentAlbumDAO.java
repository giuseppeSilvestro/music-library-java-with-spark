package com.giuseppeSilvestro.musiclibrary.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class NoPersistentAlbumDAO implements AlbumDAO{
    private List<Album> albums;

    public NoPersistentAlbumDAO() {
        albums = new ArrayList<>();
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
            if (album.getTitle().contains(title) && album.getComposer().contains(composer) && album.getDirector().contains(director) && album.getOrchestra().contains(orchestra)
            && album.getMainPerformer().contains(mainPerformer) && album.getLiveStudio().contains(liveStudio) && album.getYear().contains(year) && album.getQuality().contains(quality)) {
                result.add(album);
            }
        }
        return result;
    }

}
