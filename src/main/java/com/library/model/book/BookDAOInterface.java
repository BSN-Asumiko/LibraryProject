package com.library.model.book;

import java.util.List;

public interface BookDAOInterface {
    List<Book> getAllBooks();

    void insertBooktoTable(Book book);

    void deleteBookByTitle(String title); // Agrega esta línea

    void updateBookTitle(String currentTitle, String newTitle);

    void relateBookAuthor(Book book);

    void relateBookGenre(Book book);

    void printTable(List<Book> books);

    void printTableWithoutDescription(List<Book> books);

    Book findBookDetailsById(int idBook);

    List<Book> findBooksByAuthorID(String author);

    List<Book> findBooksByGendreID(String genre);
}
