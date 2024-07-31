package com.library.model;


public interface GenreDAOInterface {
    void insertGenretoTable(Book book);
    void findGenresByBookId(int idBook);
    
} 
