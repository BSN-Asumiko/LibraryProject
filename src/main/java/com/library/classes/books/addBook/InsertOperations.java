package com.library.classes.books.addBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.library.config.DBManager;
import com.library.model.Book;
import com.library.utils.DatabaseUtils;

public class InsertOperations {

    public static void insertBooktoTable (Book book) {
        
        DatabaseUtils.updateBookIdSequence();

        String insertBookQuery = "INSERT INTO books (title, description, isbn) VALUES (?, ?, ?)";
        boolean bookExists = DatabaseUtils.checkExisting("books", "title", book.getTitle());

        if (bookExists) {
            System.out.println("A book with this title already exists in the table 'books'.");
            return;
        }
    
        try (Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertBookQuery)) {
    
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setString(3, book.getIsbn());
    
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Failed to add the book.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertAuthortoTable (Book book) {

        DatabaseUtils.updateAuthorIdSequence();
        List<String> authors = book.getAuthors();
        String insertAuthorQuery = "INSERT INTO authors (name) VALUES (?)";
    
        for (String author : authors) {

            boolean authorExists = DatabaseUtils.checkExisting("authors", "name", author);
            if (authorExists) {
                System.out.println("This author already exists in the table 'authors'.");
                continue;
            }

            try (Connection connection = DBManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertAuthorQuery)) {
    
                preparedStatement.setString(1, author);
    
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Author added successfully.");
                } else {
                    System.out.println("Failed to add the author.");
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertGenretoTable (Book book) {

        DatabaseUtils.updateGenreIdSequence();
        List<String> genres = book.getGenres();
        String insertGenreQuery = "INSERT INTO genres (name) VALUES (?)";

        for (String genre : genres) {

            boolean genreExists = DatabaseUtils.checkExisting("genres", "name", genre);
            if (genreExists) {
                System.out.println("This genre already exists in the table 'genres'.");
                continue;
            }
    
            try (Connection connection = DBManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertGenreQuery)) {
    
                preparedStatement.setString(1, genre);
    
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Genre added successfully.");
                } else {
                    System.out.println("Failed to add the genre.");
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void relateBookGenre (Book book) {

        List<String> genres = book.getGenres();
        int id_book = DatabaseUtils.findIdByValue("id_book","books", "title", book.getTitle());
        String insertIdQuery = "INSERT INTO book_genre (id_book, id_genre) VALUES (?, ?)";

        if (id_book == -1) {
            System.out.println("Failed to find the book ID. Book might not have been inserted.");
            return;
        }

        for (String genre : genres) {
            int id_genre = DatabaseUtils.findIdByValue("id_genre","genres", "name", genre);

            if (id_genre == -1) {
                System.out.println("Genre '" + genre + "' does not exist. Skipping insertion into book_genre.");
                continue;
            }
            
            try (Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertIdQuery)) {
    
            preparedStatement.setInt(1, id_book);
            preparedStatement.setInt(2,id_genre);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book-genre relationship added successfully.");
            } else {
                System.out.println("Failed to add book-genre relationship.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }

    public static void relateBookAuthor (Book book) {

        List<String> authors = book.getAuthors();
        int id_book = DatabaseUtils.findIdByValue("id_book","books", "title", book.getTitle());
        String insertIdQuery = "INSERT INTO book_author (id_book, id_author) VALUES (?, ?)";

        for (String author : authors) {
            int id_author = DatabaseUtils.findIdByValue("id_author","authors", "name", author);

            if (id_author == -1) {
                System.out.println("Author '" + author + "' does not exist. Skipping insertion into book_author.");
                continue;
            }
            
            try (Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertIdQuery)) {
    
            preparedStatement.setInt(1, id_book);
            preparedStatement.setInt(2,id_author);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book-author relationship added successfully.");
            } else {
                System.out.println("Failed to add book-author relationship.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }

}

