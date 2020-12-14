package com.giuseppeSilvestro.musiclibrary.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Album implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;
    @Column
    private String composer;
    @Column
    private String orchestra;
    @Column
    private String director;
    @Column
    private String mainPerformer;
    @Column
    private String liveStudio;
    @Column
    private String year;
    @Column
    private String quality;

    public Album() {

    }

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

    //Builder pattern to improve code readability and clarity
    public Album(AlbumBuilder albumBuilder) {
        this.title = albumBuilder.title;
        this.composer = albumBuilder.composer;
        this.orchestra = albumBuilder.orchestra;
        this.director = albumBuilder.director;
        this.mainPerformer = albumBuilder.mainPerformer;
        this.liveStudio = albumBuilder.liveStudio;
        this.year = albumBuilder.year;
        this.quality = albumBuilder.quality;
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


    public static class AlbumBuilder {

        private String title;
        private String composer;
        private String orchestra;
        private String director;
        private String mainPerformer;
        private String liveStudio;
        private String year;
        private String quality;

        public AlbumBuilder(String title, String composer) {
            this.title = title;
            this.composer = composer;
        }

        public AlbumBuilder withOrchestraAndDirector(String orchestra, String director) {
            this.orchestra = orchestra;
            this.director = director;
            return this;
        }

        public AlbumBuilder withMainPerformer(String mainPerformer) {
            this.mainPerformer = mainPerformer;
            return this;
        }

        public AlbumBuilder withLiveStudio(String liveStudio) {
            this.liveStudio = liveStudio;
            return this;
        }

        public AlbumBuilder withYear(String year) {
            this.year = year;
            return this;
        }

        public AlbumBuilder withQuality(String quality) {
            this.quality = quality;
            return this;
        }

        public Album build() {
            return new Album(this);
        }
    }
}