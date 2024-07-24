package com.library.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.library.utils.DatabaseConnection;

public class BookTitle {

    public void buscarLibroPorTitulo(String titulo) {
        String query = "SELECT * FROM books WHERE title ILIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + titulo + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_book");
                    String tituloLibro = rs.getString("title");
                    String descripcion = rs.getString("description");
                    String isbn = rs.getString("isbn");

                    // Imprime la información del libro encontrado
                    System.out.println("ID: " + id);
                    System.out.println("Título: " + tituloLibro);
                    System.out.println("Descripción: " + descripcion);
                    System.out.println("ISBN: " + isbn);
                } else {
                    System.out.println("No se encontró ningún libro con el título: " + titulo);
                }
            } catch (SQLException e) {
                System.err.println("Error al procesar el resultado: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BookTitle repo = new BookTitle();
        repo.buscarLibroPorTitulo("Persiguiendo a Silvia");
    }
}
