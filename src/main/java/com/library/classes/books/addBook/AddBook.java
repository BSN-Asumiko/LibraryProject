package com.library.classes.books.addBook;

import com.library.classes.books.Books;

public class AddBook {

    public static void addBook (Books book) {
        
        InsertOperations.insertBooktoTable(book);
        InsertOperations.insertAuthortoTable(book);
        InsertOperations.insertGenretoTable(book);
        InsertOperations.relateBookAuthor(book);
        InsertOperations.relateBookGenre(book);
    }
}
