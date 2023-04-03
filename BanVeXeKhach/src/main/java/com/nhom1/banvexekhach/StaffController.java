/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.Ticket;
import com.nhom1.services.TicketServices;
import com.nhom1.utils.MessageBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author fptshop.com.vn
 */
public class StaffController implements Initializable{
    @FXML private TableView<Ticket> tbTicket;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {           
            this.loadTableTicket();
            this.loadTableData();
        } catch (SQLException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadTableTicket() throws SQLException {
        TableColumn colIDTicket = new TableColumn("Mã vé");
        colIDTicket.setCellValueFactory(new PropertyValueFactory("id"));
        colIDTicket.setPrefWidth(100);
        
        TableColumn colChair = new TableColumn("Ghế");
        colChair.setCellValueFactory(new PropertyValueFactory("chair"));
        colChair.setPrefWidth(100);
        
        TableColumn colStatus = new TableColumn("Trạng thái");
        colStatus.setCellValueFactory(new PropertyValueFactory("status"));
        colStatus.setPrefWidth(150);
        
        TableColumn colStartTime = new TableColumn("Mã chuyến đi");
        colStartTime.setCellValueFactory(new PropertyValueFactory("trip_id"));
        colStartTime.setPrefWidth(120);
        
        TableColumn colStart = new TableColumn("Khách hàng");
        colStart.setCellValueFactory(new PropertyValueFactory("customer_id"));
        colStart.setPrefWidth(120);
        
        TableColumn colChangeTicket = new TableColumn();
        colChangeTicket.setCellFactory(p -> {
                Button btnChange = new Button("Đổi vé");
            
                btnChange.setOnAction(evt -> {
                    Alert confirm = MessageBox.getBox("Question", 
                            "Are you sure to delete this question", 
                            Alert.AlertType.CONFIRMATION);
                    confirm.showAndWait().ifPresent(res -> {
                        if (res == ButtonType.OK) {
                        }
                    });
                });
                TableCell cellChange = new TableCell();
                cellChange.setGraphic(btnChange);
                return cellChange;
        });
        
        TableColumn colCancelTicket = new TableColumn();
        colCancelTicket.setCellFactory(evt -> {
                Button btnCancel = new Button("Hủy vé");
                TableCell cellCancel = new TableCell();
                cellCancel.setGraphic(btnCancel);
                return cellCancel;
        });
        
        this.tbTicket.getColumns().addAll(colIDTicket, colChair, colStatus, 
                colStartTime, colStart, colChangeTicket, colCancelTicket);
    }
    
    private void loadTableData() throws SQLException {
        TicketServices s = new TicketServices();
        List<Ticket> tk = s.loadTicket();
        this.tbTicket.setItems(FXCollections.observableList(tk));
    }
}