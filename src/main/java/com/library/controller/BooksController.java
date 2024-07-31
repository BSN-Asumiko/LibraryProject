package com.library.controller;

import java.util.List;

import com.library.model.AuthorDAOInterface;
import com.library.model.Book;
import com.library.model.BookDAOInterface;
import com.library.model.GenreDAOInterface;
import com.library.model.utils.DatabaseUtils;

public class BooksController {

    private BookDAOInterface bookDAOInterface;
    private AuthorDAOInterface authorDAOInterface;
    private GenreDAOInterface genreDAOInterface;

    public BooksController(BookDAOInterface bookDAOInterface) {
        this.bookDAOInterface = bookDAOInterface;
    }

    public void AuthorsController(AuthorDAOInterface authorDAOInterface) {
        this.authorDAOInterface = authorDAOInterface;
    }

    public void GenresController(GenreDAOInterface genreDAOInterface) {
        this.genreDAOInterface = genreDAOInterface;
    }

    public void  getAllBooks() {
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
