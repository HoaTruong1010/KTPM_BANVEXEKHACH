/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.Route;
import com.nhom1.pojo.Trip;
import com.nhom1.pojo.User;
import com.nhom1.services.TripServices;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author fptshop.com.vn
 */
public class SaleTicketController extends BookingController implements Initializable {
    @FXML
    private TableView<Trip> tableTrip;
    @FXML
    private ComboBox<Route> cbFillterRoute;
    @FXML
    private TextField txtKeyword;
    @FXML
    private Label lbStart;
    @FXML
    private Label lbStartText;
    @FXML
    private Label lbEndText;
    @FXML
    private Label lbEnd;
    @FXML
    private Button btBook;
    @FXML
    private Label lbCurrentUsername;
    private User currentUser;
    
    @Override
    public void loadTableData(String kw, int routeID) throws SQLException {
        TripServices t = new TripServices();
        List<Trip> list = t.loadTrips(kw, routeID);

        list = list.stream().filter((Trip x) -> {
            LocalDateTime departing = LocalDateTime.parse(x.getDeparting_at(), Trip.formatDate);
            Date now = new Date();
            long getTime = departing.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long getDiff = getTime - now.getTime();
            return getDiff > 5000;

        }).collect(Collectors.toList());

        this.tableTrip.setItems(FXCollections.observableList(list));
    }
}
