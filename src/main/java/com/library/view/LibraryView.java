package com.library.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.library.controller.BooksController;
import com.library.model.book.Book;

public class LibraryView {
  private BooksController booksController;

  public LibraryView(BooksController booksController) {
    this.booksController = booksController;
  }

  public void manageLibrary() {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("");
      System.out.println("Bienvenido a nuestra Libreria, ¿Que te gustaría hacer? ");
      System.out.println("1. Ver listado completo de libros.");
      System.out.println("2. Buscar libro por titulo.");
      System.out.println("3. Buscar libro por autor.");
      System.out.println("4. Buscar libro por genero.");
      System.out.println("5. Añadir un libro.");
      System.out.println("6. Eliminar libro por titulo.");
      System.out.println("7. Modificar el titulo del libro.");
      System.out.println("8. Salir de la libreria.");
      System.out.println("-------------------------------------------------------");

      int response = scanner.nextInt();
      scanner.nextLine();

      switch (response) {
        case 1:
          booksController.getAllBooks();
          break;

        case 2:
          System.out.print("Introduce el nombre del libro que quieres buscar: ");
          String title = scanner.nextLine();
          booksController.filterByTitle(title);
          break;

        case 3:
          System.out.print("Introduce el nombre del autor que quieres buscar: ");
          String author = scanner.nextLine();
          booksController.filterByAuthor(author);
          break;

        case 4:
          System.out.print("Introduce el nombre del genero que quieres buscar: ");
          String genre = scanner.nextLine();
          booksController.filterByGenre(genre);
          break;

        case 5:
          System.out.print("Para añadir un libro a la libreria, Primero: escribe el titulo: ");
          String newTitle = scanner.nextLine();
          System.out.print("Segundo: escribe una breve descripción(Max 200 caracteres): ");
          String newDescription = scanner.nextLine();
          System.out.print("Tercero: escribe el ISBN: ");
          String newIsbn = scanner.nextLine();
          System.out.print("Cuarto: escribe el autor o autores, uno por uno: ");
          List<String> authors = new ArrayList<>();
          String author1 = scanner.nextLine();
          authors.add(author1);
          boolean next = false;
          while (!next) {
            System.out.print("¿Este libro tiene mas autores? si/no: ");
            String answer = scanner.nextLine();
            if (answer.equals("si")) {
              System.out.print("Escribe el otro autor: ");
              String author2 = scanner.nextLine();
              authors.add(author2);
            } else {
              next = true;
            }
          }
          System.out.print("Quinto: escribe el genero o generos, uno por uno: ");
          List<String> genres = new ArrayList<>();
          String genre1 = scanner.nextLine();
          genres.add(genre1);
          boolean follow = false;
          while (!follow) {
            System.out.print("¿Este libro tiene mas genero? si/no: ");
            String answer = scanner.nextLine();
            if (answer.equals("si")) {
              System.out.print("Escribe el otro genero: ");
              String genre2 = scanner.nextLine();
              genres.add(genre2);
            } else {
              follow = true;
            }
          }
          Book book = new Book(newTitle, newDescription, newIsbn, authors, genres);
          booksController.addBook(book);
          break;

        case 6:
          System.out.print("Introduce el titulo del libro que deseas eliminar: ");
          String bookDeleted = scanner.nextLine();
          booksController.deleteBookByTitle(bookDeleted);
          break;

        case 7:
          System.out.print("Escribe el titulo del libro que quieres modificar: ");
          String currentTitle = scanner.nextLine();

          System.out.print("Escribe el nuevo titulo del libro seleccionado: ");
          String updatedTitle = scanner.nextLine();
          booksController.updateBookTitle(currentTitle, updatedTitle);
          break;

        case 8:
          scanner.close();
          System.exit(0);
          break;
        default:
          System.out.println("Opción no valida, intentalo de nuevo");
      }
    }
  }
}
