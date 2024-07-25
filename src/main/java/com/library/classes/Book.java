package com.library.classes;

public class Book {
    private String title;
    private String author;
    private String description;
    private long isbn;
    private String genre;

    public Book(String title, String author, String description, long isbn, String genre) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public long getIsbn() {
        return isbn;
    }

    public String getGenre() {
        return genre;
    }
}
