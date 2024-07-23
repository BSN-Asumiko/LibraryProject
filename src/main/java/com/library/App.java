package com.library;

import java.util.List;
import com.library.classes.ManageBooks;

public class App {
    public static void main(String[] args) {


        ManageBooks bookDAO = new ManageBooks();

        // Fetch all books
        List<String> books = bookDAO.getAllBooks();
        for (String book : books) {
            System.out.println("Book Title: " + book);
        }
    }
    
}