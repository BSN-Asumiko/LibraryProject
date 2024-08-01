package com.library.model.author;

import java.util.List;

import com.library.model.book.Book;

public interface AuthorDAOInterface {
    void insertAuthortoTable(Book book);

    List<String> findAuthorsByBookId(int idBook);
    int findAuthorIDByAuthorName(String author);
    void deleteOrphanAuthors( );
}
