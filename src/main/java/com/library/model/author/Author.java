package com.library.model.author;

public class Author {

    private int id_author;
    private String name;

    public Author(int id_author, String name) {
        this.id_author = id_author;
        this.name = name;
    }

    public int getId() {
        return id_author;
    }

    public String getName() {
        return name;
    }

}
