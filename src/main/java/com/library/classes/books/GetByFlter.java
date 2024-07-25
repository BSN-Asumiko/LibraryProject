package com.library.classes.books;

import java.util.List;

public class GetByFlter {
    private final BooksTitle manageBooks;
    private final UpdateBook updateBook;

    public GetByFlter() {
        this.manageBooks = new BooksTitle();
        this.updateBook = new UpdateBook();
    }

    public void displayBookDetailsByTitle(String title) {
        int bookId = manageBooks.findBookIdByTitle(title);
        if (bookId != -1) {
            Books book = manageBooks.findBookDetailsById(bookId);
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

    public void updateBookTitleByTitle(String currentTitle, String newTitle) {
        int bookId = manageBooks.findBookIdByTitle(currentTitle);
        if (bookId != -1) {
            boolean success = updateBook.updateBookTitle(bookId, newTitle);
            if (success) {
                System.out.println("Book title updated from '" + currentTitle + "' to '" + newTitle + "'.");
            } else {
                System.out.println("Failed to update the book title.");
            }
        } else {
            System.out.println("No book found with the title: " + currentTitle);
        }
    }

    public static void main(String[] args) {
        GetByFlter bookDetails = new GetByFlter();
        String title = "Un cuento perfecto"; 
        bookDetails.displayBookDetailsByTitle(title);

        // Actualizar el título del libro
        bookDetails.updateBookTitleByTitle("Un cuento perfecto", "Un cuento actualizado");
    }
}