package com.library;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.library.classes.books.Books;
import com.library.classes.books.DeleteBooks;
import com.library.classes.books.GetBooks;
import com.library.classes.books.GetByFlter;
import com.library.classes.books.Table;
import com.library.classes.books.addBook.AddBook;

public class App {
    public static void main(String[] args) {
        GetBooks bookDAO = new GetBooks();
        List<Books> books = bookDAO.getAllBooks();
        Table.printTable(books);

        List<String> authors = Arrays.asList("Terry Pratchett");
        List<String> genres = Arrays.asList("Fantasy", "Comedy");


        Books book = new Books("Mort", "Mort has been chosen as Death apprentice. The trouble begins when instead of collecting the soul of a princess, he kills her would-be assassin, and changes history.",
            "9780552144292", authors, genres);

        AddBook.addBook(book);

        GetByFlter bookDetails = new GetByFlter();
        String title = "Un cuento perfecto"; 
        bookDetails.displayBookDetailsByTitle(title);
    }
}

