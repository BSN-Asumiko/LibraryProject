package com.library.controller;

import java.util.List;

import com.library.model.Book;
import com.library.model.BookDAOInterface;


public class BooksController {
    
    private BookDAOInterface bookDAOInterface;

    public BooksController(BookDAOInterface bookDAOInterface){
        this.bookDAOInterface = bookDAOInterface;
    }

    public List<Book> getAllBooks(){
        List<Book> books = bookDAOInterface.getAllBooks();
        return books;
    }


}
