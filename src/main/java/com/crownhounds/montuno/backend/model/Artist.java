package com.crownhounds.montuno.backend.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Artist {

    // OOP ENCAPSULATION private class fields
    private SimpleIntegerProperty _id;
    private SimpleStringProperty name;

    // OOP constructor that initializes the class fields on class/object instantiation
    public Artist() {
        this._id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
    }

    // OOP getters & setters
    public int get_id() {
        return this._id.get();
    }

    public void set_id(int _id) {
        this._id.set(_id);
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
}