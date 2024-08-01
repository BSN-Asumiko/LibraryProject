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

    @Override
    public void insertAuthortoTable(Book book) {
        List<String> authors = book.getAuthors();
        if (authors == null || authors.isEmpty()) {
            System.out.println("No authors provided.");
            return;
        }

        String createSeqQuery = "CREATE SEQUENCE IF NOT EXISTS authors_id_seq START WITH 1 INCREMENT BY 1";
        String insertAuthorQuery = "INSERT INTO authors (name) VALUES (?) ON CONFLICT DO NOTHING";

        try (Connection connection = DBManager.initConnection();
                PreparedStatement createSeqStatement = connection.prepareStatement(createSeqQuery)) {

            createSeqStatement.execute();

            for (String author : authors) {
                boolean authorExists = DatabaseUtils.checkExisting("authors", "name", author);
                if (authorExists) {
                    System.out.println("Author '" + author + "' already exists in the table 'authors'.");
                    continue;
                }

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertAuthorQuery)) {
                    preparedStatement.setString(1, author);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Author '" + author + "' added successfully.");
                    } else {
                        System.out.println("Failed to add the author '" + author + "'.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection();
        }
    }

    @Override
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

    @Override
    public int findAuthorIDByAuthorName(String author) {
        DatabaseUtils databaseUtils = new DatabaseUtils();
        return databaseUtils.findIdByValue("id_author", "authors", "name", author);
    }
}
