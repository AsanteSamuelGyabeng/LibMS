package com.example.libms.controllers;

import com.example.libms.SceneController;
import com.example.libms.model.UserModel;
import com.example.libms.util.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;

/**
 * The type Login controller.
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private UserModel userModel;
    private Stage stage;
    private Scene scene;
    private EventObject event;

    /**
     * Instantiates a new Login controller.
     *
     * @throws SQLException the sql exception
     */
    public LoginController() throws SQLException {
        this.userModel = new UserModel();
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticateUser(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/libms/dashboard.fxml"));
                Scene scene = new Scene(loader.load());

                // Get the controller of the dashboard
                LibraryDashboardController dashboardController = loader.getController();

                // Pass the username to the dashboard controller
                dashboardController.setUsername(username);

                // Show the dashboard scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Library Dashboard");
                stage.show();
            } catch (IOException | SQLException e) {
                System.err.println(e.getMessage());
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the dashboard.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username or password.");
        }
    }


    /**
     * @param
     * @param password
     * @return
     *
     * @authentiateUser
     * */
    private boolean authenticateUser(String username, String password) {
        // SQL query to check if the username and password exist in the database
        String sql = "SELECT * FROM staff WHERE username = ? AND password = ?";

        try (Connection connection = DB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            showAlert(AlertType.ERROR, "Database Error", "An error occurred while verifying credentials.");
        }

        return false;
    }

    /**
     * @showAlert method to display alerts to the user
     * */
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
