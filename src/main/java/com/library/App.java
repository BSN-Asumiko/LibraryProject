package com.library;
import java.util.List;

import com.library.classes.books.Books;
import com.library.classes.books.GetBooks;
import com.library.classes.books.Table;
import com.library.classes.books.UpdateBook;

public class App {
    public static void main(String[] args) {
        GetBooks bookDAO = new GetBooks();
        List<Books> books = bookDAO.getAllBooks();
        Table.printTable(books);
    

        UpdateBook bookDao = new UpdateBook();
        System.out.println(bookDao);
        
}

}