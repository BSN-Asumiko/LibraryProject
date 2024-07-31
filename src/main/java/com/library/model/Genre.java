package com.library.model;

public class Genre {
    private int id_genre;
    private String name;

    public Genre(int id_genre, String name) {
        this.id_genre = id_genre;
        this.name = name;
    }

    public int getId() {
        return id_genre;
    }

    public String getName() {
        return name;
    }
}
