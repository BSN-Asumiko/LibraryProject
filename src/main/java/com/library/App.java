package com.library;

import com.library.controller.BooksController;
import com.library.model.book.BookDAO;
import com.library.model.book.BookDAOInterface;
import com.library.model.author.AuthorDAO;
import com.library.model.author.AuthorDAOInterface;
import com.library.model.genre.GenreDAO;
import com.library.model.genre.GenreDAOInterface;
import com.library.view.LibraryView;

public class App {
        public static void main(String[] args) {
                BookDAOInterface bookDao = new BookDAO();
                AuthorDAOInterface authorDao = new AuthorDAO();
                GenreDAOInterface genreDao = new GenreDAO();

                BooksController booksController = new BooksController(bookDao, authorDao, genreDao);
                LibraryView libraryView = new LibraryView(booksController);

                libraryView.manageLibrary();
        }
}
