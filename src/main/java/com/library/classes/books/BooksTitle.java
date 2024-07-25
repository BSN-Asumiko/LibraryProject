package com.library.classes.books;

import com.library.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BooksTitle {

    // Obtener todos los libros de la base de datos.
     public List<String> getAllBooks() {
        List<String> books = new ArrayList<>();
        String query = "SELECT title FROM books";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                books.add(title);
            }

        } catch (SQLException e) {
            System.err.println("Error durante la obtención de libros de la base de datos.");
            e.printStackTrace();
        }

        return books;
    } 

    // Busca el ID de un libro basado en su título.
    public int findBookIdByTitle(String title) {
        String query = "SELECT id_book FROM books WHERE title ILIKE ?";
        int idBook = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + title + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    idBook = rs.getInt("id_book");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idBook;
    }

    // Obtener detalles del libro por id
    public Book findBookDetailsById(int idBook) {
        String query = "SELECT title, description, isbn FROM books WHERE id_book = ?";
        Book book = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idBook);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    book = new Book(rs.getString("title"), rs.getString("description"), rs.getString("isbn"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    // Obtener géneros del libro por id
    public List<String> findGenresByBookId(int idBook) {
        String query = "SELECT g.name FROM book_genre bg JOIN genres g ON bg.id_genre = g.id_genre WHERE bg.id_book = ?";
        List<String> genres = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idBook);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    genres.add(rs.getString("name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genres;
    }

    // Obtener autores del libro por id
    public List<String> findAuthorsByBookId(int idBook) {
        String query = "SELECT a.name FROM book_author ba JOIN authors a ON ba.id_author = a.id_author WHERE ba.id_book = ?";
        List<String> authors = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idBook);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    authors.add(rs.getString("name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return authors;
    }
}
