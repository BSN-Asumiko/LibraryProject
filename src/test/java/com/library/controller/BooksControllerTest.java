package com.library.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.library.model.author.AuthorDAOInterface;
import com.library.model.book.Book;
import com.library.model.book.BookDAOInterface;
import com.library.model.genre.GenreDAOInterface;
import com.library.model.utils.DatabaseUtils;

import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BooksControllerTest {

    @Mock
    private BookDAOInterface bookDAOInterface;

    @Mock
    private AuthorDAOInterface authorDAOInterface;

    @Mock
    private GenreDAOInterface genreDAOInterface;

    @InjectMocks
    private BooksController booksController;

    String title = "New Book";
    String description = "A new book description";
    String Isbn = "123456789";
    List<String> authors = List.of("Author1", "Author2");
    List<String> genres = List.of("Genre1", "Genre2");

    Book book = new Book(title, description, Isbn, authors, genres);

    @Test
    public void testAddBook() {

        /*
         * String title = "New Book";
         * String description = "A new book description";
         * String Isbn = "123456789";
         * List<String> authors = List.of("Author1", "Author2");
         * List<String> genres = List.of("Genre1", "Genre2");
         * 
         * Book book = new Book(title, description, Isbn, authors, genres);
         */

        booksController.addBook(book);

        verify(bookDAOInterface).insertBooktoTable(book);
        verify(authorDAOInterface).insertAuthortoTable(book);
        verify(genreDAOInterface).insertGenretoTable(book);
        verify(bookDAOInterface).relateBookAuthor(book);
        verify(bookDAOInterface).relateBookGenre(book);
    }

    @Test
    public void testAddBook_BookAlreadyExists() throws Exception {

        /*
         * String title = "New Book";
         * String description = "A new book description";
         * String Isbn = "123456789";
         * List<String> authors = List.of("Author1", "Author2");
         * List<String> genres = List.of("Genre1", "Genre2");
         * 
         * Book book = new Book(title, description, Isbn, authors, genres);
         */

        try (MockedStatic<DatabaseUtils> bookExists = mockStatic(DatabaseUtils.class)) {
            bookExists.when(() -> DatabaseUtils.checkExisting("books", "title", book.getTitle()))
                    .thenReturn(true);

            booksController.addBook(book);

            verify(bookDAOInterface, never()).insertBooktoTable(book);
            verify(authorDAOInterface, never()).insertAuthortoTable(book);
            verify(genreDAOInterface, never()).insertGenretoTable(book);
            verify(bookDAOInterface, never()).relateBookAuthor(book);
            verify(bookDAOInterface, never()).relateBookGenre(book);
        }
    }

}
