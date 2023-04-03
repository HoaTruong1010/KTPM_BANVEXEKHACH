/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.Route;
import com.nhom1.pojo.Trip;
import com.nhom1.services.RouteServices;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author HOA TRƯƠNG
 */
public class Booking_detailController implements Initializable {
@FXML
    private Label lbID;
    @FXML
    private Label lbDeparting;
    @FXML
    private Label lbArriving;
    @FXML
    private Label lbPrice;
    @FXML
    private Label lbRoute;
    @FXML
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setTripDetail(Trip trip) throws SQLException {
        lbID.setText(String.valueOf(trip.getId()));
        lbDeparting.setText(trip.getDeparting_at());
        lbArriving.setText(trip.getArriving_at());
        lbPrice.setText(String.valueOf(trip.getPrice()));
        Route r = RouteServices.getRouteById(trip.getRoute_id());
        lbRoute.setText(String.format("%s - %s", r.getStart(), r.getEnd()));
    }
}
