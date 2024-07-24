package com.library.classes;

import com.library.dao.ManageBooks;
import com.library.model.Book;

import java.util.List;

public class BookDetails {
    private final ManageBooks manageBooks;

    public BookDetails() {
        this.manageBooks = new ManageBooks();
    }

    public void displayBookDetailsByTitle(String title) {
        int bookId = manageBooks.findBookIdByTitle(title);
        if (bookId != -1) {
            Book book = manageBooks.findBookDetailsById(bookId);
            System.out.println("Title: " + book.getTitle());
            System.out.println("Description: " + book.getDescription());
            System.out.println("ISBN: " + book.getIsbn());

            List<String> genres = manageBooks.findGenresByBookId(bookId);
            System.out.println("Genres: " + String.join(", ", genres));

            List<String> authors = manageBooks.findAuthorsByBookId(bookId);
            System.out.println("Authors: " + String.join(", ", authors));
        } else {
            System.out.println("No book found with the title: " + title);
        }
    }

    public static void main(String[] args) {
        BookDetails bookDetails = new BookDetails();
        String title = "Harry Potter y la Cámara Secreta"; 
        bookDetails.displayBookDetailsByTitle(title);
    }
}
