package com.library;

import com.library.controller.BooksController;
import com.library.model.author.AuthorDAO;
import com.library.model.author.AuthorDAOInterface;
import com.library.model.book.BookDAO;
import com.library.model.book.BookDAOInterface;
import com.library.model.genre.GenreDAO;
import com.library.model.genre.GenreDAOInterface;
import com.library.view.LibraryView;

/* import java.util.Arrays;
import java.util.List; */

//import com.library.classes.books.GetByFilter;

//import com.library.model.Book;

public class App {
    public static void main(String[] args) {
      BookDAOInterface bookDAO = new BookDAO();
AuthorDAOInterface authorDAO = new AuthorDAO();
GenreDAOInterface genreDAO = new GenreDAO();

BooksController booksController = new BooksController(bookDAO, authorDAO, genreDAO);

        LibraryView libraryView = new LibraryView(booksController);

        libraryView.manageLibrary();
/*         List<String> authors = Arrays.asList("Terry Pratchett");
        List<String> genres = Arrays.asList("Fantasy", "Comedy"); */


/*         Book book = new Book("Mort", "Mort has been chosen as Death apprentice. The trouble begins when instead of collecting the soul of a princess, he kills her would-be assassin, and changes history.",
            "9780552144292", authors, genres); */


    /*     GetByFilter bookDetails = new GetByFilter();
        String title = "Un cuento perfecto"; 
        bookDetails.displayBookDetailsByTitle(title);
    }

    GetByFilter bookDetails = new GetByFilter();
    String title = "Un cuento perfecto"; 
    bookDetails.displayBookDetailsByTitle(title);

    // Actualizar el título del libro
    bookDetails.updateBookTitleByTitle("Un cuento perfecto", "Un cuento actualizado");  */
}
}

