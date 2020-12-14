package com.giuseppeSilvestro.musiclibrary;

import com.giuseppeSilvestro.musiclibrary.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    private static final String FLASH_MESSAGE_KEY = "flash_message";

    public static void main(String[] args) throws ClassNotFoundException {
        staticFileLocation("/public");

        AlbumDAO albumDAO = new HibernateAlbumDAO();

        before((request, response) -> {
            if (request.cookie("username") != null) {
                request.attribute("username", request.cookie("username"));
            }
        });

        before("/music-library", (request, response) -> {
            if (request.attribute("username") == null) {
                response.redirect("/");
                halt();
            }
        });

        get("/", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            model.put("flashMessage", captureFlashMessage(request));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/index", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            String username = request.queryParams("username");
            String email = request.queryParams("email");
            if (isEmpty(username, email)){
                setFlashMessage(request,"Please insert your username and email address");
                response.redirect("/");
            } else {
                response.cookie("username", username);
                response.cookie("email", email);
                model.put("username", username);
            }
            response.redirect("/music-library");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/albumList", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("albums", albumDAO.findAll());
            return new ModelAndView(model, "albumList.hbs");
        }, new HandlebarsTemplateEngine());

        get("/music-library", (request, response) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", request.attribute("username"));
            model.put("flashMessage", captureFlashMessage(request));
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
            model.put("flashMessage", captureFlashMessage(request));
            return new ModelAndView(model, "addAlbum.hbs");
        }, new HandlebarsTemplateEngine());

        post("/addAlbum", (request, response) -> {
            String title = request.queryParams("title");
            String composer = request.queryParams("composer");
            String orchestra = request.queryParams("orchestra");
            String director = request.queryParams("director");
            String mainPerformer = request.queryParams("mainPerformer");
            String liveStudio = request.queryParams("liveStudio");
            String year = request.queryParams("year");
            String quality = request.queryParams("quality");
            if (isEmpty(title, composer, orchestra, director, quality)){
                setFlashMessage(request, "Some fields are empty. Please fill the form correctly.");
            } else {
            Album album = new Album(title, composer, orchestra, director, mainPerformer, liveStudio, year, quality);
            albumDAO.add(album);
            }
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

    private static void setFlashMessage(Request request, String message) {
        request.session().attribute(FLASH_MESSAGE_KEY, message);
    }

    private static String getFlashMessage(Request request) {
        if (request.session(false) == null) {
            return null;
        }
        if (!request.session().attributes().contains(FLASH_MESSAGE_KEY)) {
            return null;
        }
        return (String) request.session().attribute(FLASH_MESSAGE_KEY);
    }
    public static String captureFlashMessage(Request request) {
        String message = getFlashMessage(request);
        if (message != null) {
            request.session().removeAttribute(FLASH_MESSAGE_KEY);
        }
        return message;
    }

    private static boolean isEmpty (String ...a) {
        for (String parameter :
                a) {
            if (parameter.isBlank()) {
                return true;
            }
        }
        return false;
    }
}
