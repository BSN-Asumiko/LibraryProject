package com.library;

import java.util.List;

import com.library.classes.manageBooks;

public class App {
    public static void main(String[] args) {

        
        manageBooks bookDAO = new manageBooks();

        // Fetch all books
        List<String> books = bookDAO.getAllBooks();
        for (String book : books) {
            System.out.println("Book Title: " + book);
        }
    }
    
}