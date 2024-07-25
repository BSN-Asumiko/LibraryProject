package com.library;

import java.util.List;


import com.library.classes.books.Books;
import com.library.classes.books.GetBooks;
import com.library.classes.books.Table;

public class App {
    public static void main(String[] args) {
        GetBooks bookDAO = new GetBooks();

        List<Books> books = bookDAO.getAllBooks();
        Table.printTable(books);
    }
}
