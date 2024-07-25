package com.library.classes.books;

import java.util.List;

public class GetByFlter {
    private final BooksTitle manageBooks;

    public GetByFlter() {
        this.manageBooks = new BooksTitle();
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

    public static void main(String[] args) {
        GetByFlter bookDetails = new GetByFlter();
        String title = "Un cuento perfecto"; 
        bookDetails.displayBookDetailsByTitle(title);
    }
}
