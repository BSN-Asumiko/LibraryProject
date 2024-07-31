package com.library.model.author;

import com.library.model.book.Book;
import java.util.List;

public interface AuthorDAOInterface {
    void insertAuthortoTable(Book book);

    List<String> findAuthorsByBookId(int idBook);

    void printAuthorsDetails(int idBook);

    int findAuthorIDByAuthorName(String author);
}
