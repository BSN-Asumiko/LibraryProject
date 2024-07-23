package com.library.classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class ManageBooks {

    public List<String> getAllBooks() {
        List<String> books = new ArrayList<>();

        try (Connection connection = com.library.utils.DatabaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM books")) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                books.add(title);
            }

        } catch (SQLException e) {
            System.err.println("Error during fetching books from database.");
            e.printStackTrace();
        }

        return books;
    }
}