package com.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.config.DBManager;
import com.library.model.utils.DatabaseUtils;

public class GenreDAO implements GenreDAOInterface {
    public void insertGenretoTable(Book book) {

        String maxIdQuery = "SELECT MAX(id_author) FROM authors";
        String setValQuery = "SELECT setval('authors_id_seq', ?, false)";

        try (Connection connection = DBManager.initConnection();
                PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery);
                ResultSet resultSet = maxIdStatement.executeQuery()) {

            long maxId = 0;
            if (resultSet.next()) {
                maxId = resultSet.getLong(1);
            }

            try (PreparedStatement setValStatement = connection.prepareStatement(setValQuery)) {
                setValStatement.setLong(1, maxId + 1);
                setValStatement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection();
        }

        List<String> genres = book.getGenres();
        String insertGenreQuery = "INSERT INTO genres (name) VALUES (?)";

        for (String genre : genres) {

            boolean genreExists = DatabaseUtils.checkExisting("genres", "name", genre);
            if (genreExists) {
                System.out.println("This genre already exists in the table 'genres'.");
                continue;
            }

            try (Connection connection = DBManager.initConnection();
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
            } finally {
                DBManager.closeConnection();
            }
        }
    }

    // Obtener géneros del libro por id
    public void findGenresByBookId(int idBook) {
        String query = "SELECT g.name FROM book_genre bg JOIN genres g ON bg.id_genre = g.id_genre WHERE bg.id_book = ?";
        List<String> genres = new ArrayList<>();

        try (Connection conn = DBManager.initConnection();
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
        } finally {
            DBManager.closeConnection();
        }

        System.out.println("Genres: " + String.join(", ", genres));
    }
    
}
