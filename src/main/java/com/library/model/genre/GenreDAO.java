package com.library.model.genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.config.DBManager;
import com.library.model.book.Book;
import com.library.model.utils.DatabaseUtils;

public class GenreDAO implements GenreDAOInterface {
    @Override
    public void insertGenretoTable(Book book) {

        String createSeqQuery = "CREATE SEQUENCE IF NOT EXISTS genres_id_seq START WITH 1 INCREMENT BY 1";
        String insertGenreQuery = "INSERT INTO genres (name) VALUES (?) ON CONFLICT DO NOTHING";

        try (Connection connection = DBManager.initConnection();
                PreparedStatement createSeqStatement = connection.prepareStatement(createSeqQuery)) {

            createSeqStatement.execute();

            List<String> genres = book.getGenres();
            for (String genre : genres) {

                boolean genreExists = DatabaseUtils.checkExisting("genres", "name", genre);
                if (genreExists) {
                    System.out.println("This genre already exists in the table 'genres'.");
                    continue;
                }

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertGenreQuery)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection();
        }
    }

    @Override
    public List<String> findGenresByBookId(int idBook) {
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

        return genres;
    }
}
