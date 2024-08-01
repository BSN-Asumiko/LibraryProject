package com.library.model.genre;

import java.util.List;

import com.library.model.book.Book;

public interface GenreDAOInterface {
    void insertGenretoTable(Book book);
    List<String> findGenresByBookId(int idBook);
    
} 
