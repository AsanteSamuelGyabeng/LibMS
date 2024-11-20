package com.example.libms.model;

import com.example.libms.util.DB;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LendBookManager {

    /**
     * @lendBook method lends a book to a student
     * */
    public void lendBook(int bookId, String studentName, int staffId, Date lendDate, Date returnDate) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DB.getConnection();
            String sql = "INSERT INTO lend_books (book_id, student_name, staff_id, lend_date, return_date) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, bookId);
            ps.setString(2, studentName);
            ps.setInt(3, staffId);
            ps.setDate(4, lendDate);
            ps.setDate(5, returnDate);


            int rowsAffected = ps.executeUpdate();

            // Provide feedback
            if (rowsAffected > 0) {
                showSuccessAlert();
            } else {
                showErrorAlert();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while lending the book: " + e.getMessage());
        } finally {
            ps.close();
           conn.close();
        }
    }

    /**
     * @showSuccessAlert for success
     */
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book lending recorded successfully!", ButtonType.OK);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * @showErrorAlert for error
     */
    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Book lending failed " + ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText("Book Lending Failed");
        alert.showAndWait();
    }

}
