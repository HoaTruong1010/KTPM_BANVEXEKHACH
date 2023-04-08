/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
/**
 *
 * @author fptshop.com.vn
 */
public class ExportTicketController implements Initializable{
    @FXML private Label lbStart;
    @FXML private Label lbEnd;
    @FXML private Label lbFrom;
    @FXML private Label lbTo;
    @FXML private Label lbEnter;
    @FXML private Label lbBusNumber;
    @FXML private Label lbQR;
    @FXML private Label lbSeat;
    @FXML private Label lbCusName;
    @FXML private Label lbUserName;
    @FXML private Label lbPrintDate;
    @FXML private Label lbTicketID;
    @FXML private Label lbStartDate;
    @FXML private Label lbCusPhone;
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
        this.lbUserName.setText(String.format("%s",
                currentUser.getName()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void setInfoTicket() {
        
    }
    
    
}
