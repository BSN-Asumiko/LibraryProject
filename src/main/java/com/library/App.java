package com.library;

import java.util.Arrays;
import java.util.List;

import com.library.classes.books.Books;
import com.library.classes.books.addBook.AddBook;

public class App {
    public static void main(String[] args) {

  // Create lists for authors and genres
        List<String> authors = Arrays.asList("Terry Pratchett");
        List<String> genres = Arrays.asList("Fantasy", "Comedy");


        Books book = new Books("Mort", 
            "Mort has been chosen as Death apprentice. The trouble begins when instead of collecting the soul of a princess, he kills her would-be assassin, and changes history.",
            "9780552144292", authors, genres);

        AddBook.addBook(book);


    }

}