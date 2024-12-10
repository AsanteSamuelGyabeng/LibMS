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
    public void initialize() {
        // Initialize the table columns
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        isAvailableColumn.setCellValueFactory(cellData -> cellData.getValue().isAvailableProperty());
        copiesColumn.setCellValueFactory(cellData -> cellData.getValue().copiesProperty().asObject());


        loadBooks();
    }

    /**
     * @loadBooks Load all books from the database and display them in the table
     */
    private void loadBooks() {
        ObservableList<Book> books = Book.getAllBooks();
        booksTable.setItems(books);
    }

    /**
     * @handleAddBook Add a new book to the database
     *
     */
    @FXML
    private void handleAddBook() {
        String title = titleField.getText();
        String genre = genreField.getText();
        String isbn = isbnField.getText();
        String isAvailable = isAvailableField.getText();
        int copies = Integer.parseInt(copiesField.getText());

        Book newBook = new Book(title, genre, isbn, isAvailable, copies);

        if (newBook.addBook()) {
            loadBooks();
            clearFields();
            showAlert("Success", "Book added successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to add book.", Alert.AlertType.ERROR);
        }
    }

    /**
     * @handleDeleteBook Delete a book from the database
     */
    @FXML
    private void handleUpdateBook() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("Error", "No book selected.", Alert.AlertType.ERROR);
            return;
        }

        selectedBook.setTitle(titleField.getText());
        selectedBook.setGenre(genreField.getText());
        selectedBook.setIsbn(isbnField.getText());
        selectedBook.setIsAvailable(isAvailableField.getText());
        selectedBook.setCopies(Integer.parseInt(copiesField.getText()));

        if (selectedBook.updateBook()) {
            loadBooks();
            clearFields();
            showAlert("Success", "Book updated successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to update book.", Alert.AlertType.ERROR);
        }
    }

    /**
     * @handleDeleteBook Delete a book from the database
      */
    @FXML
    private void handleDeleteBook() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("Error", "No book selected.", Alert.AlertType.ERROR);
            return;
        }

        if (selectedBook.deleteBook()) {
            loadBooks();
            clearFields();
            showAlert("Success", "Book deleted successfully!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Failed to delete book.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleSearchBook() {
        String isbn = searchIsbnField.getText(); // Get the ISBN entered by the user

        if (isbn == null || isbn.isEmpty()) {
            showAlert("Error", "Please enter an ISBN to search.", Alert.AlertType.ERROR);
            return;
        }
        Book book = Book.searchBookByIsbn(isbn);

        if (book != null) {
            booksTable.getSelectionModel().clearSelection();
            ObservableList<Book> searchResults = FXCollections.observableArrayList();
            searchResults.add(book);
            booksTable.setItems(searchResults);
        } else {
            showAlert("Not Found", "No book found with the given ISBN.", Alert.AlertType.INFORMATION);
        }
    }
    @FXML
    private void handleClearFields() {
        clearFields();
    }

    /**
     * Helper method to clear the fields
     */
    private void clearFields() {
        titleField.clear();
        genreField.clear();
        isbnField.clear();
        isAvailableField.clear();
        copiesField.clear();
    }

    /**
     * Helper method to show an alert dialog
     * @param title
     * @param message
     * @param alertType
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Navigation icon to go back to the previous page
     * @param event
     * @throws IOException
     */
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.switchScene("dashboard.fxml", event);
    }

}
