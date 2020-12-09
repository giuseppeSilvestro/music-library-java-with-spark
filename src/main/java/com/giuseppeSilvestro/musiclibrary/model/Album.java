package com.giuseppeSilvestro.musiclibrary.model;

import java.io.Serializable;

public class Album implements Serializable {
    private String title;
    private String composer;
    private String orchestra;
    private String director;
    private String mainPerformer;
    private String liveStudio;
    private String year;
    private String quality;

    public Album(String title, String composer, String orchestra, String director,
                 String mainPerformer, String liveStudio, String year, String quality) {
        this.title = title;
        this.composer = composer;
        this.orchestra = orchestra;
        this.director = director;
        this.mainPerformer = mainPerformer;
        this.liveStudio = liveStudio;
        this.year = year;
        this.quality = quality;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getOrchestra() {
        return orchestra;
    }

    public void setOrchestra(String orchestra) {
        this.orchestra = orchestra;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getMainPerformer() {
        return mainPerformer;
    }

    public void setMainPerformer(String mainPerformer) {
        this.mainPerformer = mainPerformer;
    }

    public String getLiveStudio() {
        return liveStudio;
    }

    public void setLiveStudio(String liveStudio) {
        this.liveStudio = liveStudio;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "Album" +
                "title= '" + title + '\'' +
                ", composer='" + composer + '\'' +
                ", orchestra='" + orchestra + '\'' +
                ", director='" + director + '\'' +
                ", mainPerformer='" + mainPerformer + '\'' +
                ", liveStudio='" + liveStudio + '\'' +
                ", year='" + year + '\'' +
                ", quality='" + quality + '\'';
    }
}
