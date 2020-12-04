package com.giuseppeSilvestro.musiclibrary;

import com.giuseppeSilvestro.musiclibrary.model.Album;
import com.giuseppeSilvestro.musiclibrary.model.AlbumDAO;
import com.giuseppeSilvestro.musiclibrary.model.NoPersistentAlbumDAO;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


//TODO: add flash messages to all methods

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");

        AlbumDAO albumDAO = new NoPersistentAlbumDAO();

        albumDAO.add(new Album("Piano Concerto n.5", "Beethoven", "London", "Rattle", "Giuseppe", "Live"
        ,"1985", "DSD256"));
        albumDAO.add(new Album("Piano Concerto n.5", "Beethoven L.W.", "Berlin", "VonKarajan", "Gennaro", "Live", "1985", "DSD256"));
        albumDAO.add(new Album("Violin Concerto n.6", "Beethoven", "San Carlo", "Verdi", "Rachmaninov", "Studio", "1889", "FLAC"));
        albumDAO.add(new Album("Piano Concerto n.1", "Rachmaninov", "Moscow", "Maviv", "Rachmaninov", "Live", "1923", "WAVE"));

        before((request, response) -> {
            if (request.cookie("username") != null) {
                request.attribute("username", request.cookie("username"));
            }
        });

        get("/", (request, response) -> new ModelAndView(null, "index.hbs"), new HandlebarsTemplateEngine());

        post("/music-library", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            final String username = request.queryParams("username");
            final String email = request.queryParams("email");
            response.cookie("username", username);
            response.cookie("email", email);
            model.put("username", username);
            return new ModelAndView(model, "music-library.hbs");
        }, new HandlebarsTemplateEngine());

        get("/albumList", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("albums", albumDAO.findAll());
            return new ModelAndView(model, "albumList.hbs");
        }, new HandlebarsTemplateEngine());

        get("/music-library", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", request.attribute("username"));
            return new ModelAndView(model, "music-library.hbs");
        }, new HandlebarsTemplateEngine());

        get("/randomAlbum", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("username", request.attribute("username"));
            model.put("album", albumDAO.randomAlbum());
            return new ModelAndView(model, "randomAlbum.hbs");
        }, new HandlebarsTemplateEngine());

        get("/addAlbum", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", request.attribute("username"));
            return new ModelAndView(model, "addAlbum.hbs");
        }, new HandlebarsTemplateEngine());

        post("/addAlbum", (request, response) -> {
            //TODO: add a function to check if the fields are empty
            String title = request.queryParams("title");
            String composer = request.queryParams("composer");
            String orchestra = request.queryParams("orchestra");
            String director = request.queryParams("director");
            String mainPerformer = request.queryParams("mainPerformer");
            String liveStudio = request.queryParams("liveStudio");
            String year = request.queryParams("year");
            String quality = request.queryParams("quality");
            Album album = new Album(title, composer, orchestra, director, mainPerformer, liveStudio, year, quality);
            albumDAO.add(album);
            response.redirect("/addAlbum");
            return null;
        });

        get("/albumSearch", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", request.attribute("username"));
            return new ModelAndView(model, "albumSearch.hbs");
        }, new HandlebarsTemplateEngine());

        post("/albumSearch", (request, response) -> {
            String title = request.queryParams("title");
            String composer = request.queryParams("composer");
            String orchestra = request.queryParams("orchestra");
            String director = request.queryParams("director");
            String mainPerformer = request.queryParams("mainPerformer");
            String liveStudio = request.queryParams("liveStudio");
            String year = request.queryParams("year");
            String quality = request.queryParams("quality");
            Map<String, Object> model = new HashMap<>();
            model.put("albums", albumDAO.findAlbum(title, composer, orchestra, director, mainPerformer, liveStudio, year, quality));
            model.put("username", request.attribute("username"));
            return new ModelAndView(model, "albumSearch.hbs");
        }, new HandlebarsTemplateEngine());

    }
}
