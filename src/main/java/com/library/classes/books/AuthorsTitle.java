package com.library.classes.books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.library.config.DBManager;

public class AuthorsTitle {
    public int findAuthorIDByAuthorName(String name) {
        String query = "SELECT id_author FROM authors WHERE name ILIKE ?";
        int idAuthor = -1;

        try (Connection conn = DBManager.initConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + name + "%");


            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    idAuthor = rs.getInt("id_author");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idAuthor;

    }


    public List<String> findBooksByAuthorID( int idAuthor){
        String query = "SELECT b.title FROM book_author ba JOIN books b on ba.id_book = b.id_book WHERE ba.id_author = ?";
        List<String> books = new ArrayList<>();

        try (Connection conn = DBManager.initConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, idAuthor);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        books.add(rs.getString("title"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return books;
    } 
}


