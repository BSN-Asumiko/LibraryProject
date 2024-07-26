package com.library;

import java.util.List;
import java.util.Scanner;

import com.library.classes.books.Books;
import com.library.classes.books.DeleteBooks;
import com.library.classes.books.GetBooks;

public class App {
    public static void main(String[] args) {
        GetBooks bookDAO = new GetBooks();
        DeleteBooks deleteDAO = new DeleteBooks();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("¿Qué quieres hacer? ");
            System.out.println("1. Ver listado de libros ");
            System.out.println("2. Eliminar libro por su ID");
            System.out.println("3. Eliminar libro por su título");
            System.out.println("4. Salir");

            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {

                case 1:
                    List<Books> books = bookDAO.getAllBooks();
                    printBooks(books);
                    break;
                case 2: 
                    System.out.print("Introduce el ID del libro que quieres borrar: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    deleteDAO.deleteBookById(id);
                    break;
                case 3: 
                    System.out.print("Introduce el título del libro que quieres borrar: ");
                    String title = scanner.nextLine();
                    deleteDAO.deleteBookByTitle(title);
                    break;
                case 4: 
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Esa no es una opción válida. Inténtalo de nuevo.");
            }
        }

        
    }

    private static void printBooks(List<Books> books) {
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-35s %-60s %-20s %-40s %-20s\n", "Book ID", "Title", "Description", "ISBN", "AUTHOR", "GENRE");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Books book : books) {
            System.out.println();
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
