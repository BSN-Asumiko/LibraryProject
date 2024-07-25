package com.library;


import com.library.classes.AddBook;
import com.library.classes.Book;

public class App {
    public static void main(String[] args) {


        Book book = new Book("Rey de patatas", "Patata", "Mort has been chosen as Death apprentice. The trouble begins when instead of collecting the soul of a princess, he kills her would-be assassin, and changes history.", 9780552144292l, "fantasy");

        // Add the book to the database
        AddBook.addBook(book);
    }

}