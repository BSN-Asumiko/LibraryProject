package com.library.config;

import java.sql.Connection;
import java.sql.DriverManager;

import io.github.cdimascio.dotenv.Dotenv; 

public class DBManager {
    
    private static final Dotenv dotenv = Dotenv.load();
    private static Connection connection;
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/library";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = dotenv.get("PASSWORD");

    
    public static Connection initConnection() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME,  PASSWORD);
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
