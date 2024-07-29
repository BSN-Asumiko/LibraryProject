package com.library.classes.books;

import java.util.List;

public class GetByFilter2 {
    private final AuthorsTitle manageBooks2;

    public GetByFilter2() {
        this.manageBooks2 = new AuthorsTitle();
    }

    public void displayBooksByAuthor(String name) {
        int authorId = manageBooks2.findAuthorIDByAuthorName(name);
        if (authorId != -1) {
            List<String> books = manageBooks2.findBooksByAuthorID(authorId);
            System.out.println("Books: " + String.join(", ", books));
        } else {
            System.out.println("No books found with the author: " + name);
        }
    }

    public static void main(String[] args) {
        GetByFilter2 booksList = new GetByFilter2();
        String name = "Elísabet Benavent"; 
        booksList.displayBooksByAuthor(name);
    }
}