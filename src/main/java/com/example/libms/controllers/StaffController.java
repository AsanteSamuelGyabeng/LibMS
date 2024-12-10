package com.example.libms.controllers;

import com.example.libms.util.DB;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StaffController {

    @FXML
    private TextField nameInput;

    @FXML
    private TextField emailInput;

    @FXML
    private PasswordField passwordInput;

    private Connection connection;

    // Constructor initializes the database connection
    public StaffController() throws SQLException, ClassNotFoundException {
        this.connection = DB.getConnection(); // Get connection from DB utility
    }

    /**
     * Handle sign-up action for registering a new staff member.
     *
     * @param username     The name of the staff member.
     * @param email    The email address of the staff member.
     * @param password The password for the staff member.
     */
    @FXML
    public void handleSignUpAction(String username, String email, String password) {
        if (areFieldsValid(username, email, password)) {
            String sql = "INSERT INTO staff (username, email, password) VALUES (?, ?, ?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert("Success", "Staff member registered successfully!");
                    clearFields();
                } else {
                    showAlert("Error", "Registration failed. Please try again.");
                }
            } catch (SQLException e) {
                showAlert("Database Error", "An error occurred: " + e.getMessage());
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Validate if the input fields are not empty.
     *
     * @param name     The name to check.
     * @param email    The email to check.
     * @param password The password to check.
     * @return true if all fields are filled, false otherwise.
     */
    private boolean areFieldsValid(String name, String email, String password) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "All fields are required.");
            return false;
        }
        return true;
    }

    /**
     * Clear the input fields.
     */
    private void clearFields() {
        nameInput.clear();
        emailInput.clear();
        passwordInput.clear();
    }

    /**
     * Show an alert message.
     *
     * @param title   The title of the alert.
     * @param message The message of the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
