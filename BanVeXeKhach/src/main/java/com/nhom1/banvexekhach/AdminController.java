/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.Trip;
import com.nhom1.services.TripServices;
import com.nhom1.utils.MessageBox;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author HOA TRƯƠNG
 */
public class AdminController implements Initializable {
    @FXML private TableView<Trip> tableTrip;
    @FXML private TextField txtID;
    @FXML private TextField txtDate;
    @FXML private TextField txtPrice;
    @FXML private ComboBox cbCar;
    @FXML private ComboBox cbRoute;
    @FXML private Button btnSearch;
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnExit;
    @FXML private TextField txtKeyword;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadTable();
        try {
            // TODO
                        
            this.loadTableData(null);
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    private void loadTable() {
        TableColumn colID = new TableColumn("Mã chuyến đi");
        colID.setCellValueFactory(new PropertyValueFactory("id"));
        colID.setPrefWidth(100);
        
        TableColumn colDate = new TableColumn("Thời gian khởi hành");
        colDate.setCellValueFactory(new PropertyValueFactory("tripDate"));
        colDate.setPrefWidth(160);
        
        TableColumn colPrice = new TableColumn("Giá 1 vé/ghế");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colPrice.setPrefWidth(125);
        
        TableColumn colCarID = new TableColumn("Số xe");
        colCarID.setCellValueFactory(new PropertyValueFactory("car_id"));
        
        TableColumn colRouteID = new TableColumn("Tuyến");
        colRouteID.setCellValueFactory(new PropertyValueFactory("route_id"));
        
        this.tableTrip.getColumns().addAll(colID, colDate, colPrice, colCarID, colRouteID);
    }
    
    private boolean checkFormat(String text) {
        try {
            LocalDateTime.parse(text, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void loadTableData(String kw) throws SQLException {
        TripServices t = new TripServices();
        
        this.tableTrip.setItems(FXCollections.observableList(t.loadTrips(kw)));
    }
    
    public void txtSearch_Click(ActionEvent e) throws SQLException {
        String kw = this.txtKeyword.getText();
        
        if(!checkFormat(kw)) {
            MessageBox.getBox("Error", "Từ khóa không đúng định dạng:\n năm-tháng-ngày giờ:phút:giây", Alert.AlertType.ERROR).show();
            kw = null;
        }
        
        this.loadTableData(kw);
    }
    
    public void btnExit_Click(ActionEvent e) {
        System.exit(0);
    }
}
