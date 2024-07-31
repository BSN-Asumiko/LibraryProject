package com.library.controller;

import java.util.List;
import com.library.model.author.AuthorDAOInterface;
import com.library.model.book.Book;
import com.library.model.book.BookDAOInterface;
import com.library.model.genre.GenreDAOInterface;
import com.library.model.utils.DatabaseUtils;

public class BooksController {

    private BookDAOInterface bookDAOInterface;
    private AuthorDAOInterface authorDAOInterface;
    private GenreDAOInterface genreDAOInterface;

    // Constructor que inicializa todas las dependencias
    public BooksController(BookDAOInterface bookDAOInterface,
            AuthorDAOInterface authorDAOInterface,
            GenreDAOInterface genreDAOInterface) {
        this.bookDAOInterface = bookDAOInterface;
        this.authorDAOInterface = authorDAOInterface;
        this.genreDAOInterface = genreDAOInterface;
    }

    // Métodos del controlador
    public void getAllBooks() {
        List<Book> books = bookDAOInterface.getAllBooks();
        bookDAOInterface.printTable(books);
    }

    public void addBook(Book book) {
        bookDAOInterface.insertBooktoTable(book);
        authorDAOInterface.insertAuthortoTable(book);
        genreDAOInterface.insertGenretoTable(book);
        bookDAOInterface.relateBookAuthor(book);
        bookDAOInterface.relateBookGenre(book);
    }

    public void updateBookTitle(String currentTitle, String newTitle) {
        bookDAOInterface.updateBookTitle(currentTitle, newTitle);
    }

    public void deleteBookByTitle(String title) {
        bookDAOInterface.deleteBookByTitle(title);
    }

    public void filterByTitle(String title) {
        DatabaseUtils databaseUtils = new DatabaseUtils();
        int idBook = databaseUtils.findIdByValue("id_book", "books", "title", title);
        bookDAOInterface.findBookDetailsById(idBook);
        genreDAOInterface.findGenresByBookId(idBook);
        authorDAOInterface.findAuthorsByBookId(idBook);
    }

    public void filterByAuthor(String author) {
        bookDAOInterface.findBooksByAuthorID(author);
    }

    public void filterByGenre(String genre) {
        bookDAOInterface.findBooksByGendreID(genre);
    }
}
