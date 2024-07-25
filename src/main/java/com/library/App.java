package com.library;

import java.util.Arrays;
import java.util.List;
import com.library.classes.books.AddBook;
import com.library.classes.books.Books;

public class App {
    public static void main(String[] args) {

  // Create lists for authors and genres
        List<String> authors = Arrays.asList("Patata");
        List<String> genres = Arrays.asList("fantasy");

        // Create a new book object using the constructor without id_book
        Books book = new Books("Conocido Rey de patatas", 
            "Mort has been chosen as Death apprentice. The trouble begins when instead of collecting the soul of a princess, he kills her would-be assassin, and changes history.",
            "9780552144292", authors, genres);

        // Assuming your AddBook class is in the same package or imported
        AddBook.addBook(book);
    }

}