package com.library.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {

    public static void updateBookIdSequence() {
        String maxIdQuery = "SELECT MAX(id_book) FROM books";
        String setValQuery = "SELECT setval('books_id_seq', ?, false)";

        try (Connection connection = DatabaseConnection.getConnection();
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
        }
    }

    public static void updateGenreIdSequence() {
        String maxIdQuery = "SELECT MAX(id_genre) FROM genres";
        String setValQuery = "SELECT setval('genres_id_seq', ?, false)";

        try (Connection connection = DatabaseConnection.getConnection();
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
        }
    }

    public static void updateAuthorIdSequence() {
        String maxIdQuery = "SELECT MAX(id_author) FROM authors";
        String setValQuery = "SELECT setval('authors_id_seq', ?, false)";

        try (Connection connection = DatabaseConnection.getConnection();
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
        }
    }

    public static boolean checkExisting (String table, String column, String value) {

        String query = "SELECT 1 FROM " + table + " WHERE " + column + " = ? LIMIT 1";

        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, value);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();  // If there is a result, the value exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int findIdByValue(String idName, String table, String columnName, String searchValue) {
        String query = "SELECT " + idName + " FROM " + table + " WHERE " + columnName + " = ? LIMIT 1";
        int id = -1;
    
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            preparedStatement.setString(1, searchValue);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(idName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return id;
    }
    
}

