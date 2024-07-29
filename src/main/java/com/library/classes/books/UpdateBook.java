package com.library.classes.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.library.config.DBManager;

public class UpdateBook {

    public boolean updateBookTitle(int idBook, String newTitle) {
        String updateBookQuery = "UPDATE books SET title = ? WHERE id_book = ?";

        try (Connection connection = DBManager.initConnection();
            PreparedStatement bookStatement = connection.prepareStatement(updateBookQuery)) {

            // Actualizar el título del libro
            bookStatement.setString(1, newTitle);
            bookStatement.setInt(2, idBook);
            int bookRowsUpdated = bookStatement.executeUpdate();

            // Retornar true si la actualización fue exitosa
            return bookRowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error durante la actualización del libro en la base de datos.");
            e.printStackTrace();
            // Retornar false si ocurre un error
            return false;
        }
    }
}