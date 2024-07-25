package com.library.classes.books;

import com.library.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetBooks {
    public List<Books> getAllBooks() {
        Map<Integer, Books> booksMap = new HashMap<>();

        String query = "SELECT b.id_book, b.title, b.description, b.isbn, " +
                "a.name AS author_name, g.name AS genre_name " +
                "FROM books b " +
                "JOIN book_author ba ON b.id_book = ba.id_book " +
                "JOIN authors a ON ba.id_author = a.id_author " +
                "JOIN book_genre bg ON b.id_book = bg.id_book " +
                "JOIN genres g ON bg.id_genre = g.id_genre " +
                "ORDER BY b.id_book ASC";

        try (Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id_book = resultSet.getInt("id_book");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String isbn = resultSet.getString("isbn");
                String authorName = resultSet.getString("author_name");
                String genreName = resultSet.getString("genre_name");

                Books book = booksMap.get(id_book);
                if (book == null) {
                    List<String> authors = new ArrayList<>();
                    List<String> genres = new ArrayList<>();
                    authors.add(authorName);
                    genres.add(genreName);
                    book = new Books(id_book, title, description, isbn, authors, genres);
                    booksMap.put(id_book, book);
                } else {
                    List<String> authors = book.getAuthors();
                    List<String> genres = book.getGenres();
                    if (!authors.contains(authorName)) {
                        authors.add(authorName);
                    }
                    if (!genres.contains(genreName)) {
                        genres.add(genreName);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error durante la recuperación de libros desde la base de datos.");
            e.printStackTrace();
        }

        return new ArrayList<>(booksMap.values());
    }
}
