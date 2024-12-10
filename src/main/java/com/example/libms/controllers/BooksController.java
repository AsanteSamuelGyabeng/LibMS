package com.example.libms.controllers;

import com.example.libms.SceneController;
import com.example.libms.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class BooksController {

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, Integer> bookIdColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> genreColumn;

    @FXML
    private TableColumn<Book, String> isbnColumn;

    @FXML
    private TableColumn<Book, String> isAvailableColumn;

    @FXML
    private TableColumn<Book, Integer> copiesColumn;

    @FXML
    private TextField titleField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField isbnField;
    @FXML
    private TextField searchIsbnField;

    @FXML
    private TextField isAvailableField;

    @FXML
    private TextField copiesField;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button clearButton;

    // Initialize method
    public void initialize() throws ClassNotFoundException {
        // Initialize the table columns
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        isAvailableColumn.setCellValueFactory(cellData -> cellData.getValue().isAvailableProperty());
        copiesColumn.setCellValueFactory(cellData -> cellData.getValue().copiesProperty().asObject());



        // Load all books from the database
        loadBooks();
    }

    // Load all books from the database
    private void loadBooks() throws ClassNotFoundException {
        ObservableList<Book> books = Book.getAllBooks();
        booksTable.setItems(books);
    }

    // Add a new book
    @FXML
    private void handleAddBook() throws ClassNotFoundException {
        String title = titleField.getText();
        String genre = genreField.getText();
        String isbn = isbnField.getText();
        String isAvailable = isAvailableField.getText();
        int copies = Integer.parseInt(copiesField.getText());

        // Create a new book object
        Book newBook = new Book(title, genre, isbn, isAvailable, copies);

        // Add the book to the database
        if (newBook.addBook()) {
            // Refresh the table after adding the book
            loadBooks();
            clearFields();
            showAlert("Success", "Book added successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to add book.", Alert.AlertType.ERROR);
        }
    }

    // Update an existing book
    @FXML
    private void handleUpdateBook() throws ClassNotFoundException {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("Error", "No book selected.", Alert.AlertType.ERROR);
            return;
        }

        // Update the book's information
        selectedBook.setTitle(titleField.getText());
        selectedBook.setGenre(genreField.getText());
        selectedBook.setIsbn(isbnField.getText());
        selectedBook.setIsAvailable(isAvailableField.getText());
        selectedBook.setCopies(Integer.parseInt(copiesField.getText()));

        // Update the book in the database
        if (selectedBook.updateBook()) {
            // Refresh the table after updating the book
            loadBooks();
            clearFields();
            showAlert("Success", "Book updated successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to update book.", Alert.AlertType.ERROR);
        }
    }

    // Delete a book
    @FXML
    private void handleDeleteBook() throws ClassNotFoundException {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("Error", "No book selected.", Alert.AlertType.ERROR);
            return;
        }

        // Delete the book from the database
        if (selectedBook.deleteBook()) {
            // Refresh the table after deleting the book
            loadBooks();
            clearFields();
            showAlert("Success", "Book deleted successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to delete book.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSearchBook() throws ClassNotFoundException {
        String isbn = searchIsbnField.getText(); // Get the ISBN entered by the user

        if (isbn == null || isbn.isEmpty()) {
            showAlert("Error", "Please enter an ISBN to search.", Alert.AlertType.ERROR);
            return;
        }

        // Search for the book by ISBN
        Book book = Book.searchBookByIsbn(isbn);

        if (book != null) {
            // Clear any previous selection in the table
            booksTable.getSelectionModel().clearSelection();

            // Add the found book to the table
            ObservableList<Book> searchResults = FXCollections.observableArrayList();
            searchResults.add(book);
            booksTable.setItems(searchResults);
        } else {
            showAlert("Not Found", "No book found with the given ISBN.", Alert.AlertType.INFORMATION);
        }
    }

    // Clear the input fields
    @FXML
    private void handleClearFields() {
        clearFields();
    }

    // Helper method to clear all input fields
    private void clearFields() {
        titleField.clear();
        genreField.clear();
        isbnField.clear();
        isAvailableField.clear();
        copiesField.clear();
    }

    // Helper method to show alert messages
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.switchScene("dashboard.fxml", event);
    }

}
