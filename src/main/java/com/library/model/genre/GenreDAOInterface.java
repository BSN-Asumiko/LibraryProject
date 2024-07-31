package com.library.model.genre;

import com.library.model.book.Book;

public interface GenreDAOInterface {
    void insertGenretoTable(Book book);
    void findGenresByBookId(int idBook);
    
} 
