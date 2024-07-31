package com.library.model.author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.config.DBManager;
import com.library.model.book.Book;
import com.library.model.utils.DatabaseUtils;

public class AuthorDAO implements AuthorDAOInterface {
    DatabaseUtils databaseUtils = new DatabaseUtils();

    public void insertAuthortoTable(Book book) {

        String maxIdQuery = "SELECT MAX(id_genre) FROM genres";
        String setValQuery = "SELECT setval('genres_id_seq', ?, false)";

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

        List<String> authors = book.getAuthors();
        String insertAuthorQuery = "INSERT INTO authors (name) VALUES (?)";

        for (String author : authors) {

            boolean authorExists = DatabaseUtils.checkExisting("authors", "name", author);
            if (authorExists) {
                System.out.println("This author already exists in the table 'authors'.");
                continue;
            }

            try (Connection connection = DBManager.initConnection();
            
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
            } finally {
                DBManager.closeConnection();
            }
        }
    }

    // Obtener autores del libro por id
    public List<String> findAuthorsByBookId(int idBook) {
        String query = "SELECT a.name FROM book_author ba JOIN authors a ON ba.id_author = a.id_author WHERE ba.id_book = ?";
        List<String> authors = new ArrayList<>();

        try (Connection conn = DBManager.initConnection();
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
        } finally {
            DBManager.closeConnection();
        }

        return authors;
    }

    public void printAuthorsDetails(int idBook) {
        List<String> authors = findAuthorsByBookId(idBook);
        System.out.println("Authors: " + String.join(", ", authors));
    }

    public int findAuthorIDByAuthorName(String author) {
        int idAuthor = databaseUtils.findIdByValue("id_author", "authors", "name", author);
        return idAuthor;
    }
}
