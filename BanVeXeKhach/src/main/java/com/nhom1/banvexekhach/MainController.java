package com.nhom1.banvexekhach;

import com.nhom1.pojo.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Button btAdmin;
    @FXML
    private Label lbCurrentUsername;
    private User currentUser;

    /**
     * @return the currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        this.lbCurrentUsername.setText(String.format("%s",
                currentUser.getName()));
    }

    public void setVisibleBtAdmin(boolean isVisible) {
        this.btAdmin.setVisible(isVisible);
    }

    public void btnAdmin_Click(ActionEvent e) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("admin.fxml"));
        Parent tripManagement = fxmlLoader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        AdminController ac = fxmlLoader.getController();
        ac.setCurrentUser(currentUser);
        stage.setScene(new Scene(tripManagement));
    }

    public void btnBooking_Click(ActionEvent e) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("booking.fxml"));
        Parent booking = fxmlLoader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        BookingController bc = fxmlLoader.getController();
        bc.setCurrentUser(currentUser);
        stage.setScene(new Scene(booking));
    }
    
    public void btnStaff_Click(ActionEvent e) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("staff.fxml"));
        Parent booking = fxmlLoader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        StaffController bc = fxmlLoader.getController();
        bc.setCurrentUser(currentUser);
        stage.setScene(new Scene(booking));
    }
    
    public void btnSaleTicket_Click(ActionEvent e) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("sale_ticket.fxml"));
        Parent booking = fxmlLoader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        SaleTicketController bc = fxmlLoader.getController();
        bc.setCurrentUser(currentUser);
        stage.setScene(new Scene(booking));
    }

    public void btnSignout_Click(ActionEvent e) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("signin.fxml"));
        Parent signout = fxmlLoader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(signout));
    }

    public void btnExit_Click(ActionEvent e) throws IOException, SQLException {
        System.exit(0);
    }
}