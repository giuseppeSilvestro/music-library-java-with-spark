package com.giuseppeSilvestro.musiclibrary.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Locale;
import java.util.Random;


public class JDBCAlbumDAO implements AlbumDAO {

    public JDBCAlbumDAO() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }

    @Override
    public void add(Album album){
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:albums.db")) {

            Statement statement = connection.createStatement();

            save(album, statement);

        } catch (SQLException e) {
            System.out.printf("There was a database error: %s%n", e.getMessage());
        }

    }

    @Override
    public List<Album> findAll() {
        List<Album> albums = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:albums.db")) {

            Statement statement = connection.createStatement();

            ResultSet selectAll = statement.executeQuery("SELECT * FROM albums");

            while (selectAll.next()) {
                Album album = getAlbumFromResultSet(selectAll);
                albums.add(album);
            }
        } catch (SQLException e) {
            System.out.printf("There was a database error: %s%n", e.getMessage());

        }
        return albums;
    }

    @Override
    public Album randomAlbum() {
        List<Album> albumList = findAll();
        Random random = new Random();
        int id = random.nextInt(albumList.size());
        return albumList.get(id);
    }

    @Override
    public List<Album> findAlbum(String title, String composer, String orchestra, String director, String mainPerformer, String liveStudio, String year, String quality) {
        List<Album> albums = findAll();
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
        return result;    }

    public static void save(Album album, Statement statement) throws SQLException {
        //get the values from the Contact object and compose an INSERT query
        String sql = "INSERT INTO albums (title, composer, orchestra, director, mainPerformer, liveStudio, year, quality) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";
        sql = String.format(sql, album.getTitle(), album.getComposer(), album.getOrchestra(), album.getDirector(), album.getMainPerformer(), album.getLiveStudio(), album.getYear(), album.getQuality());

        //execute the query
        statement.executeUpdate(sql);
    }

    private Album getAlbumFromResultSet(ResultSet select) throws SQLException {
        String title = select.getString("title");
        String composer = select.getString("composer");
        String orchestra = select.getString("orchestra");
        String director = select.getString("director");
        String mainPerformer = select.getString("mainPerformer");
        String liveStudio = select.getString("liveStudio");
        String year = select.getString("year");
        String quality = select.getString("quality");
        Album album = new Album(title, composer, orchestra, director, mainPerformer, liveStudio, year, quality);
        return album;
    }

}
