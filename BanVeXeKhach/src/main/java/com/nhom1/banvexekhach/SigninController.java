package com.nhom1.banvexekhach;

import com.nhom1.pojo.User;
import com.nhom1.services.UserServices;
import com.nhom1.utils.MessageBox;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SigninController {
    
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    
    public void btnExit_Click() {
        System.exit(0);
    }
    
    public void btnSignin_Click(ActionEvent e) throws IOException, SQLException {
        User u = UserServices.getUserByUsername(txtUsername.getText(), txtPassword.getText());
        if (u != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
            Parent main = fxmlLoader.load();
            
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            MainController mc = fxmlLoader.getController();
            mc.setCurrentUser(u);
            if (u.getUserRole().equalsIgnoreCase("admin")) {
                mc.setVisibleBtAdmin(true);
            } else {
                mc.setVisibleBtAdmin(false);
            }
            stage.setScene(new Scene(main));
        } else {
            MessageBox.getBox("Error", "Tên đăng nhập hoặc mật khẩu không đúng!", Alert.AlertType.ERROR).show();
        }
    }
}