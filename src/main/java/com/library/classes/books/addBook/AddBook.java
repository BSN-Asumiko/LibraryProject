package com.library.classes.books.addBook;

import com.library.model.Book;

public class AddBook {

    public static void addBook (Book book) {
        
        InsertOperations.insertBooktoTable(book);
        InsertOperations.insertAuthortoTable(book);
        InsertOperations.insertGenretoTable(book);
        InsertOperations.relateBookAuthor(book);
        InsertOperations.relateBookGenre(book);
    }
}
