package com.crownhounds.montuno.backend.model;

public class SongArtist {

    // OOP ENCAPSULATION private class fields
    private String artistName;
    private String albumName;
    private Integer track;

    // OOP setters & getters
    public String getArtistName() {
        return this.artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getTrack() {
        return track;
    }

    public void setTrack(Integer track) {
        this.track = track;
    }
}