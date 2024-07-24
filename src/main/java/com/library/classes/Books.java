package com.library.classes;

import java.util.List;

public class Books {
    private int id_book;
    private String title;
    private String description;
    private String isbn;
    private List<String> authors;
    private List<String> genres;

    public Books(int id_book, String title, String description, String isbn, List<String> authors,
            List<String> genres) {
        this.id_book = id_book;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.authors = authors;
        this.genres = genres;
    }

    public int getId() {
        return id_book;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getIsbn() {
        return isbn;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getGenres() {
        return genres;
    }
}
