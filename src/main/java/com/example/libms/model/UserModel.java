package com.example.libms.model;

import com.example.libms.util.DB;

import java.io.IOException;
import java.sql.*;

public class UserModel {

    // Use the static method to get the connection
    private Connection connection;

    public UserModel() throws SQLException {
        this.connection = DB.getConnection();
    }

    // Method to check login credentials
    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM staff WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next();// If a matching user is found, return true
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
