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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BooksControllerTest {

    @Mock
    private BookDAOInterface bookDAOInterface;

    @Mock
    private AuthorDAOInterface authorDAOInterface;

    @Mock
    private GenreDAOInterface genreDAOInterface;

    @Mock
    private DatabaseUtils databaseUtils;


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

    @Test
    public void testDeleteBookByTitle_BookExists() throws Exception {
        
        String title = "Existing Book";

        doNothing().when(bookDAOInterface).deleteBookByTitle(title);
        booksController.deleteBookByTitle(title);

        verify(bookDAOInterface, times(1)).deleteBookByTitle(title);
    }

    @Test
    public void testDeleteBookByTitle_BookDoesNotExist() {

    String nonExistingTitle = "Non-Existing Book";

    doThrow(new RuntimeException("Book not found")).when(bookDAOInterface).deleteBookByTitle(nonExistingTitle);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        booksController.deleteBookByTitle(nonExistingTitle);
    });
    
    assertEquals("Book not found", exception.getMessage());
    verify(bookDAOInterface, times(1)).deleteBookByTitle(nonExistingTitle);
}

    @Test
    public void testFilterByAuthorAuthorExists() {
        String author = "Test Author";
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Title", "Description", "ISBN", new ArrayList<>(), new ArrayList<>()));

        when(bookDAOInterface.findBooksByAuthorID(author)).thenReturn(books);
        when(authorDAOInterface.findAuthorsByBookId(anyInt())).thenReturn(new ArrayList<>());
        when(genreDAOInterface.findGenresByBookId(anyInt())).thenReturn(new ArrayList<>());

        booksController.filterByAuthor(author);

        verify(bookDAOInterface).printTable(anyList());
    }

    @Test
    public void testFilterByAuthorAuthorDoesNotExist() {
        String author = "Nonexistent Author";

        when(bookDAOInterface.findBooksByAuthorID(author)).thenReturn(new ArrayList<>());

        booksController.filterByAuthor(author);

        verify(bookDAOInterface, never()).printTable(anyList());
    }

    @Test
    public void testFilterByGenreGenreExists() {
        String genre = "Test Genre";
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Title", "Description", "ISBN", new ArrayList<>(), new ArrayList<>()));

        when(bookDAOInterface.findBooksByGendreID(genre)).thenReturn(books);
        when(authorDAOInterface.findAuthorsByBookId(anyInt())).thenReturn(new ArrayList<>());
        when(genreDAOInterface.findGenresByBookId(anyInt())).thenReturn(new ArrayList<>());

        booksController.filterByGenre(genre);

        verify(bookDAOInterface).printTableWithoutDescription(anyList());
    }

    @Test
    public void testFilterByGenreGenreDoesNotExist() {
        String genre = "Nonexistent Genre";

        when(bookDAOInterface.findBooksByGendreID(genre)).thenReturn(new ArrayList<>());

        booksController.filterByGenre(genre);

        verify(bookDAOInterface, never()).printTableWithoutDescription(anyList());
    }
}
