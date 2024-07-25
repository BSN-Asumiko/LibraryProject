package com.library;

import java.util.List;
import com.library.classes.books.Books;
import com.library.classes.books.GetBooks;
import com.library.classes.books.UpdateBook;

public class App {
    public static void main(String[] args) {
        GetBooks bookDAO = new GetBooks();
        UpdateBook updateDAO = new UpdateBook();

        // Mostrar todos los libros antes de la actualización
        System.out.println("Libros antes de la actualización:");
        List<Books> books = bookDAO.getAllBooks();
        printBooks(books);

        // Actualizar un libro (Ejemplo: libro con id 1)
        int bookIdToUpdate = 1; // Cambia esto según el ID del libro que quieres actualizar
        String newTitle = "Nuevo Título";
        String newAuthor = "Nuevo Autor";

        boolean updateSuccess = updateDAO.updateBook(bookIdToUpdate, newTitle, newAuthor);

        if (updateSuccess) {
            System.out.println("\nEl libro fue actualizado exitosamente.");
        } else {
            System.out.println("\nHubo un error al actualizar el libro.");
        }

        // Mostrar todos los libros después de la actualización
        System.out.println("\nLibros después de la actualización:");
        books = bookDAO.getAllBooks();
        printBooks(books);
    }

    private static void printBooks(List<Books> books) {
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-35s %-60s %-20s %-40s %-20s\n", "Book ID", "Title", "Description", "ISBN", "AUTHOR", "GENRE");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Books book : books) {
            printBook(book);
        }
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void printBook(Books book) {
        String description = book.getDescription();
        final int descriptionWidth = 50;

        String[] lines = splitString(description, descriptionWidth);
        String authors = String.join(", ", book.getAuthors());
        String genres = String.join(", ", book.getGenres());

        System.out.printf("%-10d %-35s %-60s %-20s %-40s %-20s\n", book.getId(), book.getTitle(), lines[0], book.getIsbn(),
                authors, genres);

        for (int i = 1; i < lines.length; i++) {
            System.out.printf("%-10s %-35s %-60s %-20s %-40s %-20s\n", "", "", lines[i], "", "","");
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