package com.library.model.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.library.config.DBManager;

public class DatabaseUtils {
    public static boolean checkExisting(String table, String column, String value) {
        String query = "SELECT 1 FROM " + table + " WHERE " + column + " = ? LIMIT 1";
        try (Connection connection = DBManager.initConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, value);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection();
        }
        return false;
    }

    public int findIdByValue(String idName, String table, String columnName, String searchValue) {
        String query = "SELECT " + idName + " FROM " + table + " WHERE " + columnName + " = ? LIMIT 1";
        int id = -1;

        try (Connection connection = DBManager.initConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, searchValue);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(idName);
                }
            } catch (SQLException e) {
                System.out.println("Failed to find the id of " + searchValue + ", there is no such data.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection();
        }

        return id;
    }
}
