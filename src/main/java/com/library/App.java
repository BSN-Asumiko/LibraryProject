package com.library;

import java.util.List;

import com.library.classes.books.BooksTitle;

public class App {
    public static void main(String[] args) {


        BooksTitle bookDAO = new BooksTitle();

        // Fetch all books
        List<String> books = bookDAO.getAllBooks();
        for (String book : books) {
            System.out.println("Book Title: " + book);
        }
    }
    
}