package com.library.model.book;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.library.config.DBManager;
import com.library.model.utils.DatabaseUtils;

public class BookDAO implements BookDAOInterface {
    DatabaseUtils databaseUtils = new DatabaseUtils();

    @Override
    public List<Book>getAllBooks () {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books"; // Ajusta la consulta según tu esquema

        try (Connection connection = DBManager.initConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String isbn = resultSet.getString("isbn");
                // Obtén los autores y géneros del libro si están en otras tablas

                // Ejemplo simplificado:
                Book book = new Book(title, description, isbn, new ArrayList<>(), new ArrayList<>());
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection();
        }

        return books;
    }

    public void printTable(List<Book> books) {
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%-10s %-35s %-60s %-20s %-40s %-20s\n", "ID", "Title", "Description", "ISBN", "Authors",
                "Genres");
        System.out.println("---------------------------------------------------------------------");

        for (Book book : books) {
            String description = book.getDescription();
            final int descriptionWidth = 50;
            String[] lines = splitString(description, descriptionWidth);
            String authors = String.join(", ", book.getAuthors());
            String genres = String.join(", ", book.getGenres());

            System.out.printf("%-10d %-35s %-60s %-20s %-40s %-20s\n", book.getId(), book.getTitle(), lines[0],
                    book.getIsbn(), authors, genres);
            for (int i = 1; i < lines.length; i++) {
                System.out.printf("%-10s %-35s %-60s %-20s %-40s %-20s\n", "", "", lines[i], "", "", "");
            }
        }
        System.out.println("---------------------------------------------------------------------");
    }

    private static String[] splitString(String str, int width) {
        if (str == null || width <= 0) {
            return new String[0];
        }

        int length = str.length();
        int numLines = (length + width - 1) / width;
        String[] lines = new String[numLines];

        for (int i = 0; i < numLines; i++) {
            int start = i * width;
            int end = Math.min(start + width, length);
            lines[i] = str.substring(start, end);
        }

        return lines;
    }

    public void insertBooktoTable(Book book) {


        String createSeqQuery = "CREATE SEQUENCE IF NOT EXISTS books_id_seq START WITH 1 INCREMENT BY 1";
        String alterTableQuery = "ALTER TABLE books ALTER COLUMN id_book SET DEFAULT nextval('books_id_seq')";
        String maxIdQuery = "SELECT MAX(id_book) FROM books";
        String setValQuery = "SELECT setval('books_id_seq', ?, false)";

        try (Connection connection = DBManager.initConnection();
            Statement createSeqStatement = connection.createStatement();
            Statement alterTableStatement = connection.createStatement();
            PreparedStatement maxIdStatement = connection.prepareStatement(maxIdQuery);
            ResultSet resultSet = maxIdStatement.executeQuery()) {

            createSeqStatement.execute(createSeqQuery);

            alterTableStatement.execute(alterTableQuery);

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
        } finally {
            DBManager.closeConnection();
        }

        String insertBookQuery = "INSERT INTO books (title, description, isbn) VALUES (?, ?, ?)";
        boolean bookExists = DatabaseUtils.checkExisting("books", "title", book.getTitle());

        if (bookExists) {
            System.out.println("A book with this title already exists in the table 'books'.");
            return;
        }

        try (Connection connection = DBManager.initConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertBookQuery)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setString(3, book.getIsbn());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Failed to add the book.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection();
        }
    }

    public void relateBookGenre(Book book) {

        List<String> genres = book.getGenres();
        int id_book = databaseUtils.findIdByValue("id_book", "books", "title", book.getTitle());
        String insertIdQuery = "INSERT INTO book_genre (id_book, id_genre) VALUES (?, ?)";

        if (id_book == -1) {
            System.out.println("Failed to find the book ID. Book might not have been inserted.");
            return;
        }

        for (String genre : genres) {
            int id_genre = databaseUtils.findIdByValue("id_genre", "genres", "name", genre);

            if (id_genre == -1) {
                System.out.println("Genre '" + genre + "' does not exist. Skipping insertion into book_genre.");
                continue;
            }

            try (Connection connection = DBManager.initConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(insertIdQuery)) {

                preparedStatement.setInt(1, id_book);
                preparedStatement.setInt(2, id_genre);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Book-genre relationship added successfully.");
                } else {
                    System.out.println("Failed to add book-genre relationship.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBManager.closeConnection();
            }
        }
    }

    public void relateBookAuthor(Book book) {
        List<String> authors = book.getAuthors();
        int id_book = databaseUtils.findIdByValue("id_book", "books", "title", book.getTitle());
        String insertIdQuery = "INSERT INTO book_author (id_book, id_author) VALUES (?, ?)";

        for (String author : authors) {
            int id_author = databaseUtils.findIdByValue("id_author", "authors", "name", author);

            if (id_author == -1) {
                System.out.println("Author '" + author + "' does not exist. Skipping insertion into book_author.");
                continue;
            }

            try (Connection connection = DBManager.initConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(insertIdQuery)) {

                preparedStatement.setInt(1, id_book);
                preparedStatement.setInt(2, id_author);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Book-author relationship added successfully.");
                } else {
                    System.out.println("Failed to add book-author relationship.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBManager.closeConnection();
            }
        }
    }

    public void updateBookTitle(String currentTitle, String newTitle) {
        int bookId = databaseUtils.findIdByValue("id_book", "books", "title", currentTitle);

        if (bookId == -1) {
            System.out.println("No book found with the title: " + currentTitle);
            return;
        }
        String updateBookQuery = "UPDATE books SET title = ? WHERE id_book = ?";

        try (Connection connection = DBManager.initConnection();
                PreparedStatement bookStatement = connection.prepareStatement(updateBookQuery)) {

            // Actualizar el título del libro
            bookStatement.setString(1, newTitle);
            // bookStatement.setInt(2, idBook);
            int bookRowsUpdated = bookStatement.executeUpdate();
            if (bookRowsUpdated > 0) {
                System.out.println("Book title updated from '" + currentTitle + "' to '" + newTitle + "'.");
            } else {
                System.out.println("Failed to update the book title.");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DBManager.closeConnection();
        }

    }

    public void deleteBookByTitle(String title) {

        int bookId = databaseUtils.findIdByValue("id_book", "books", "title", title);

        if (bookId == -1) {
            System.out.println("No book found with the title: " + title);
            return;
        }
        String SQL_DELETE = "DELETE FROM books WHERE id_book = ?";

        try (Connection connection = DBManager.initConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {

            preparedStatement.setInt(1, bookId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Libro eliminado correctamente");
            } else {
                System.out.println("No se ha encontrado ningún libro con ese título");

            }

        } catch (SQLException e) {
            System.err.println("Error durante la eliminación del libro desde la base de datos");
            e.printStackTrace();

        } finally {
            DBManager.closeConnection();
        }

    }


    public void findBookDetailsById(int idBook) {
        String query = "SELECT title, description, isbn FROM books WHERE id_book = ?";
        Book book = null;

        try (Connection conn = DBManager.initConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idBook);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    book = new Book(rs.getString("title"), rs.getString("description"), rs.getString("isbn"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection();
        }
        System.out.println("Title: " + book.getTitle());
        System.out.println("Description: " + book.getDescription());
        System.out.println("ISBN: " + book.getIsbn());
    }

    public List<Book> findBooksByAuthorID(String author) {
        int idAuthor = databaseUtils.findIdByValue("id_author", "authors", "name", author);
        String query = "SELECT b.id_book, b.title, b.description, b.isbn FROM book_author ba JOIN books b on ba.id_book = b.id_book WHERE ba.id_author = ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBManager.initConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idAuthor);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book(
                            rs.getInt("id_book"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("isbn"),
                            new ArrayList<>(), // Agrega autores si es necesario
                            new ArrayList<>() // Agrega géneros si es necesario
                    );
                    books.add(book);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection();
        }

        return books;
    }

    public List<Book> findBooksByGendreID(String genre) {
        int idGenre = databaseUtils.findIdByValue("id_genre", "genres", "name", genre);
        String query = "SELECT b.id_book, b.title, b.description, b.isbn FROM book_genre bg JOIN books b on bg.id_book = b.id_book WHERE bg.id_genre = ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBManager.initConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idGenre);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book(
                            rs.getInt("id_book"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("isbn"),
                            new ArrayList<>(), // Agrega autores si es necesario
                            new ArrayList<>() // Agrega géneros si es necesario
                    );
                    books.add(book);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.closeConnection();
        }

        return books;
    }
}