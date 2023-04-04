package com.nhom1.banvexekhach;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    public void btnAdmin_Click(ActionEvent e) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("admin.fxml"));
        Parent tripManagement = fxmlLoader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(tripManagement));
    }
    
    public void btnBooking_Click(ActionEvent e) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("booking.fxml"));
        Parent booking = fxmlLoader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(booking));
    }
    
    public void btnSignout_Click(ActionEvent e) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        Parent signout = fxmlLoader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(signout));
    }
    
    public void btnExit_Click(ActionEvent e) throws IOException, SQLException {
        System.exit(0);
    }
}