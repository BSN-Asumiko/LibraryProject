package com.library.classes.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.library.utils.DatabaseConnection;

public class DeleteBooks {

    public boolean deleteBookById(int id) {
        String SQL_DELETE = "DELETE FROM books WHERE id_book = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {

                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Libro eliminado correctamente");
                    return true;
                } else {
                    System.out.println("No se ha encontrado ningún libro con esa ID");
                    return false;
                }
             } catch (SQLException e) {
                System.err.println("Error durante la eliminación del libro desde la base de datos");
                e.printStackTrace();
                return false;
             }
    }

    public boolean deleteBookByTitle(String title) {
        String SQL_DELETE = "DELETE FROM books WHERE title = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {

                preparedStatement.setString(1, title);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Libro eliminado correctamente");
                    return true;  
                } else {
                    System.out.println("No se ha encontrado ningún libro con ese título");
                    return false;
                }

             } catch (SQLException e) {
                System.err.println("Error durante la eliminación del libro desde la base de datos");
                e.printStackTrace();
                return false;
             }

    }

}