package com.library.controller;

import java.util.ArrayList;
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

    public BooksController(BookDAOInterface bookDAOInterface,
            AuthorDAOInterface authorDAOInterface,
            GenreDAOInterface genreDAOInterface) {
        this.bookDAOInterface = bookDAOInterface;
        this.authorDAOInterface = authorDAOInterface;
        this.genreDAOInterface = genreDAOInterface;
    }

    public void getAllBooks() {
        List<Book> books = bookDAOInterface.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("La base de datos está vacia");
        } else {
        bookDAOInterface.printTable(books);
        }
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

    public void filterByTitle(String title) {
        DatabaseUtils databaseUtils = new DatabaseUtils();
        int idBook = databaseUtils.findIdByValue("id_book", "books", "title", title);
        if (idBook == -1) {
            System.out.println("El libro introducido no existe en la base de datos");
        } else {
            Book filteredBook = bookDAOInterface.findBookDetailsById(idBook);

            String foundTitle = filteredBook.getTitle();
            String foundDescription = filteredBook.getDescription();
            String foundIsbn = filteredBook.getIsbn();
            List<String> foundAuthors = authorDAOInterface.findAuthorsByBookId(idBook);
            List<String> foundGenres = genreDAOInterface.findGenresByBookId(idBook);

            Book foundBook = new Book(idBook, foundTitle, foundDescription, foundIsbn, foundAuthors, foundGenres);
            List<Book> foundBooks = new ArrayList<>();
            foundBooks.add(foundBook);
            bookDAOInterface.printTable(foundBooks);
        }
        
    }

    public void filterByAuthor(String author) {
        List<Book> books = bookDAOInterface.findBooksByAuthorID(author);
        
        if (books.isEmpty()) {
            System.out.println("El autor introducido no existe en la base de datos");
        } else {
            List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            int idBook = book.getId();
            String title = book.getTitle();
            String description = book.getDescription();
            String isbn = book.getIsbn();
            List<String> authors = authorDAOInterface.findAuthorsByBookId(idBook);
            List<String> genres = genreDAOInterface.findGenresByBookId(idBook);
            Book filteredBook = new Book(idBook, title, description, isbn, authors, genres);
            filteredBooks.add(filteredBook);
        }
        bookDAOInterface.printTable(filteredBooks);
        }
    }

    public void filterByGenre(String genre) {
        List<Book> books = bookDAOInterface.findBooksByGendreID(genre);
        if (books.isEmpty()) {
            System.out.println("El genero introducido no existe en la base de datos");
        } else {
            List<Book> filteredBooks = new ArrayList<>();
            for (Book book : books) {
                int idBook = book.getId();
                String title = book.getTitle();
                String description = book.getDescription();
                String isbn = book.getIsbn();
                List<String> authors = authorDAOInterface.findAuthorsByBookId(idBook);
                List<String> genres = genreDAOInterface.findGenresByBookId(idBook);
                Book filteredBook = new Book(idBook, title, description, isbn, authors, genres);
                filteredBooks.add(filteredBook);
            }
            bookDAOInterface.printTableWithoutDescription(filteredBooks);
        }
    }

    public void deleteBookByTitle(String title) {
        bookDAOInterface.deleteBookByTitle(title);
    }
}
