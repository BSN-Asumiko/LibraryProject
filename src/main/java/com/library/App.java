package com.library;

import java.util.List;
import com.library.classes.Books;
import com.library.classes.GetBooks;

public class App {
    public static void main(String[] args) {
        GetBooks bookDAO = new GetBooks();

        List<Books> books = bookDAO.getAllBooks();

        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-30s %-60s %-20s %-40s %-20s\n", "Book ID", "Title", "Description", "ISBN", "AUTHOR", "GENRE");
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Books book : books) {
            System.out.println();

            printBook(book);
        }
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void printBook(Books book) {
        String description = book.getDescription();
        final int descriptionWidth = 50;

        String[] lines = splitString(description, descriptionWidth);
        String authors = String.join(", ", book.getAuthors());
        String genres = String.join(", ", book.getGenres());

        System.out.printf("%-10d %-30s %-60s %-20s %-40s %-20s\n", book.getId(), book.getTitle(), lines[0], book.getIsbn(),
                authors, genres);

        for (int i = 1; i < lines.length; i++) {
            System.out.printf("%-10s %-30s %-60s %-20s %-40s %-20s\n", "", "", lines[i], "", "","");
        }
    }

    private static String[] splitString(String str, int width) {
        if (str == null || width <= 0) {
            return new String[0];
        }

        int length = str.length();
        int numLines = (length + width - 1) / width;
        String[] lines = new String[numLines];

        for (int i = 0; i < numLines; i++) {
            int start = i * width;
            int end = Math.min(start + width, length);
            lines[i] = str.substring(start, end);
        }

        return lines;
    }
}
