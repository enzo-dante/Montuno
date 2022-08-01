package com.crownhounds.montuno.backend.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Album {

    // OOP ENCAPSULATION private class fields
    private SimpleIntegerProperty _id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty artistId;

    public Album() {
        this._id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.artistId = new SimpleIntegerProperty();
    }

    // OOP getters & setters
    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int get_id() {
        return _id.get();
    }

    public void set_id(int _id) {
        this._id.set(_id);
    }

    public int getArtistId() {
        return artistId.get();
    }

    public void setArtistId(int artistId) {
        this.artistId.set(artistId);
    }
}
