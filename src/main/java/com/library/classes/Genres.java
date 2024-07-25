package com.library.classes;

public class Genres {
    private int id_genre;
    private String name;

    public Genres(int id_genre, String name) {
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
