package com.library.classes.books;

import com.library.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateBook {

    public boolean updateBook(int idBook, String newTitle, String newAuthor) {
        String updateBookQuery = "UPDATE books SET title = ? WHERE id_book = ?";
        String updateAuthorQuery = "UPDATE authors SET name = ? WHERE id_author = " +
                    "(SELECT id_author FROM book_author WHERE id_book = ?)";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement bookStatement = connection.prepareStatement(updateBookQuery);
            PreparedStatement authorStatement = connection.prepareStatement(updateAuthorQuery)) {

            // Actualizar el título del libro
            bookStatement.setString(1, newTitle);
            bookStatement.setInt(2, idBook);
            int bookRowsUpdated = bookStatement.executeUpdate();

            // Actualizar el nombre del autor
            authorStatement.setString(1, newAuthor);
            authorStatement.setInt(2, idBook);
            int authorRowsUpdated = authorStatement.executeUpdate();

            // Retornar true si ambas actualizaciones fueron exitosas
            return bookRowsUpdated > 0 && authorRowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error durante la actualización del libro en la base de datos.");
            e.printStackTrace();
            // Retornar false si ocurre un error
            return false;
        }
    }
}