package com.library.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.library.utils.DatabaseConnection;
import com.library.utils.DatabaseUtils;

public class AddBook {
    public static void addBook(Book book) {
        DatabaseUtils.updateBookIdSequence();
    
        System.out.println("Checking if book exists: " + book.getTitle());
        boolean bookExists = DatabaseUtils.checkExisting("books", "title", book.getTitle());
        if (bookExists) {
            System.out.println("A book with this title already exists in the table 'books'.");
            return;
        }
    
        System.out.println("Inserting book: " + book.getTitle() + ", " + book.getDescription() + ", " + book.getIsbn());
        String insertBookQuery = "INSERT INTO books (title, description, isbn) VALUES (?, ?, ?)";
    
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertBookQuery)) {
    
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setLong(3, book.getIsbn());
    
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Failed to add the book.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        DatabaseUtils.updateGenreIdSequence();
    
        System.out.println("Checking if genre exists: " + book.getGenre());
        boolean genreExists = DatabaseUtils.checkExisting("genres", "name", book.getGenre());
        if (genreExists) {
            System.out.println("This genre already exists in the table 'genres'.");
        }
    
        System.out.println("Inserting genre: " + book.getGenre());
        String insertGenreQuery = "INSERT INTO genres (name) VALUES (?)";
    
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertGenreQuery)) {
    
            preparedStatement.setString(1, book.getGenre());
    
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Genre added successfully.");
            } else {
                System.out.println("Failed to add the genre.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        DatabaseUtils.updateAuthorIdSequence();
    
        System.out.println("Checking if author exists: " + book.getAuthor());
        boolean authorExists = DatabaseUtils.checkExisting("authors", "name", book.getAuthor());
        if (authorExists) {
            System.out.println("This author already exists in the table 'authors'.");
        }
    
        System.out.println("Inserting author: " + book.getAuthor());
        String insertAuthorQuery = "INSERT INTO authors (name) VALUES (?)";
    
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertAuthorQuery)) {
    
            preparedStatement.setString(1, book.getAuthor());
    
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
