package com.crownhounds.montuno.backend.model;

public class SongArtist {

    // OOP ENCAPSULATION private class fields
    private String artistName;
    private String albumName;
    private Integer track;
    private String songTitle;
    private Integer song_id;

    // ! OOP POLYMORPHISM + INTERFACE: uniquely implement/@Override publicly shared method for designated classes
    @Override
    public String toString() {
        return "SongArtist{" +
                "artistName='" + artistName + '\'' +
                ", albumName='" + albumName + '\'' +
                ", track=" + track +
                ", songTitle='" + songTitle + '\'' +
                ", song_id=" + song_id +
                '}';
    }

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

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public Integer getSong_id() {
        return song_id;
    }

    public void setSong_id(Integer song_id) {
        this.song_id = song_id;
    }
}