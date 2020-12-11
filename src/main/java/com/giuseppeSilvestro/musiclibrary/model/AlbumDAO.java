package com.giuseppeSilvestro.musiclibrary.model;

import java.io.FileNotFoundException;
import java.util.List;

public interface AlbumDAO {
    void add(Album album);

    List<Album> findAll();
    Album randomAlbum();
    List<Album> findAlbum(String title, String composer, String orchestra, String director, String mainPerformer, String liveStudio, String year, String quality);
}
