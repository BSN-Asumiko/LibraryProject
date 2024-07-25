package com.library.classes.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.library.utils.DatabaseConnection;
import com.library.utils.DatabaseUtils;

public class AddBook {
    public static void addBook(Books book) {

        //Handle books table
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
    
        // Handle genres table
        DatabaseUtils.updateGenreIdSequence();
        List<String> genres = book.getGenres();
        for (String genre : genres) {
            System.out.println("Checking if genre exists: " + genre);
            boolean genreExists = DatabaseUtils.checkExisting("genres", "name", genre);
            if (genreExists) {
                System.out.println("This genre already exists in the table 'genres'.");
                continue;
            }
    
            System.out.println("Inserting genre: " + genre);
            String insertGenreQuery = "INSERT INTO genres (name) VALUES (?)";
    
            try (Connection connection = DatabaseConnection.getConnection();
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
    
        // Handle authors table
        DatabaseUtils.updateAuthorIdSequence();
        List<String> authors = book.getAuthors();
        for (String author : authors) {
            System.out.println("Checking if author exists: " + author);
            boolean authorExists = DatabaseUtils.checkExisting("authors", "name", author);
            if (authorExists) {
                System.out.println("This author already exists in the table 'authors'.");
                continue;
            }
    
            System.out.println("Inserting author: " + author);
            String insertAuthorQuery = "INSERT INTO authors (name) VALUES (?)";
    
            try (Connection connection = DatabaseConnection.getConnection();
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

        //Handle book_genre table
        int id_book = DatabaseUtils.findIdByValue("id_book","books", "title", book.getTitle());

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

            System.out.println("Inserting book: " + book.getTitle() + ", " + book.getDescription() + ", " + book.getIsbn());
            String insertIdQuery = "INSERT INTO book_genre (id_book, id_genre) VALUES (?, ?)";
            try (Connection connection = DatabaseConnection.getConnection();
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


        //Handle author table
        for (String author : authors) {
            int id_author = DatabaseUtils.findIdByValue("id_author","authors", "name", author);

            if (id_author == -1) {
                System.out.println("Author '" + author + "' does not exist. Skipping insertion into book_author.");
                continue;
            }

            String insertIdQuery = "INSERT INTO book_author (id_book, id_author) VALUES (?, ?)";
            try (Connection connection = DatabaseConnection.getConnection();
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
