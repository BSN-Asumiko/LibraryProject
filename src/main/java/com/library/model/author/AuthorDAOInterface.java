package com.library.model;

import java.util.List;

public interface AuthorDAOInterface {
    void insertAuthortoTable(Book book);
    List<String> findAuthorsByBookId(int idBook);
    void printAuthorsDetails(int idBook);
}
