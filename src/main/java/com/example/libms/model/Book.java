package com.example.libms.model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import com.example.libms.util.DB;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {

    private IntegerProperty bookId;
    private StringProperty title;
    private StringProperty genre;
    private StringProperty isbn;
    private StringProperty isAvailable;
    private IntegerProperty copies;

    // Constructor for Book
    public Book(int bookId, String title, String genre, String isbn, String isAvailable, int copies) {
        this.bookId = new SimpleIntegerProperty(bookId);
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.isbn = new SimpleStringProperty(isbn);
        this.isAvailable = new SimpleStringProperty(isAvailable);
        this.copies = new SimpleIntegerProperty(copies);
    }

    // Constructor for adding or updating a book
    public Book(String title, String genre, String isbn, String isAvailable, int copies) {
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.isbn = new SimpleStringProperty(isbn);
        this.isAvailable = new SimpleStringProperty(isAvailable);
        this.copies = new SimpleIntegerProperty(copies);
    }


    // Getters and Setters for JavaFX properties
    public IntegerProperty bookIdProperty() {
        return bookId; // Returns IntegerProperty
    }

    public int getBookId() {
        return bookId.get(); // Returns the int value of bookId
    }

    public void setBookId(int bookId) {
        this.bookId.set(bookId); // Sets the int value of bookId
    }

    public StringProperty titleProperty() {
        return title; // Returns StringProperty
    }

    public String getTitle() {
        return title.get(); // Returns the value of title
    }

    public void setTitle(String title) {
        this.title.set(title); // Sets the value of title
    }

    public StringProperty genreProperty() {
        return genre; // Returns StringProperty
    }

    public String getGenre() {
        return genre.get(); // Returns the value of genre
    }

    public void setGenre(String genre) {
        this.genre.set(genre); // Sets the value of genre
    }

    public StringProperty isbnProperty() {
        return isbn; // Returns StringProperty
    }

    public String getIsbn() {
        return isbn.get(); // Returns the value of isbn
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn); // Sets the value of isbn
    }

    public StringProperty isAvailableProperty() {
        return isAvailable; // Returns StringProperty
    }

    public String getIsAvailable() {
        return isAvailable.get(); // Returns the value of isAvailable
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable.set(isAvailable); // Sets the value of isAvailable
    }

    public IntegerProperty copiesProperty() {
        return copies; // Returns IntegerProperty
    }

    public int getCopies() {
        return copies.get(); // Returns the value of copies
    }

    public void setCopies(int copies) {
        this.copies.set(copies); // Sets the value of copies
    }

    // Fetch books from the database (as you had it before)
    public static ObservableList<Book> getAllBooks() {
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM books";

        try (Connection connection = DB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String genre = resultSet.getString("genre");
                String isbn = resultSet.getString("isbn");
                String isAvailable = resultSet.getString("is_available");
                int copies = resultSet.getInt("copies");

                bookList.add(new Book(bookId, title, genre, isbn, isAvailable, copies));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return bookList;
    }
    // Add a book to the database
    // Add a new book to the database
    public boolean addBook() {
        String sql = "INSERT INTO books (title, genre, isbn, is_available, copies) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, this.title.get());  // Access the value using get()
            preparedStatement.setString(2, this.genre.get());   // Access the value using get()
            preparedStatement.setString(3, this.isbn.get());    // Access the value using get()
            preparedStatement.setString(4, this.isAvailable.get());  // Access the value using get()
            preparedStatement.setInt(5, this.copies.get());    // Access the value using get()

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    // Update a book in the database
    public boolean updateBook() {
        String sql = "UPDATE books SET title = ?, genre = ?, is_available = ?, copies = ? WHERE isbn = ?";
        try (Connection connection = DB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, this.title.get());     // Access the value using get()
            preparedStatement.setString(2, this.genre.get());      // Access the value using get()
            preparedStatement.setString(3, this.isAvailable.get()); // Access the value using get()
            preparedStatement.setInt(4, this.copies.get());       // Access the value using get()
            preparedStatement.setString(5, this.isbn.get());      // Access the value using get()

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    // Delete a book from the database
    public boolean deleteBook() {
        String sql = "DELETE FROM books WHERE isbn = ?";
        try (Connection connection = DB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, this.isbn.get());  // Access the value using get()
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    // Search for a book by ISBN
    public static Book searchBookByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (Connection connection = DB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String genre = resultSet.getString("genre");
                String isAvailable = resultSet.getString("is_available");
                int copies = resultSet.getInt("copies");

                return new Book(bookId, title, genre, isbn, isAvailable, copies);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null; // Return null if no book is found
    }


}
