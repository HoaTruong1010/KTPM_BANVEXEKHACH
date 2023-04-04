/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.Customer;
import com.nhom1.pojo.Route;
import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.Trip;
import com.nhom1.services.CustomerServices;
import com.nhom1.services.RouteServices;
import com.nhom1.services.TicketServices;
import com.nhom1.utils.CheckData;
import com.nhom1.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HOA TRƯƠNG
 */
public class Booking_detailController implements Initializable {

    @FXML
    private GridPane root;
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
    private TextField txtName;
    @FXML
    private TextField txtPhone;
    List<Ticket> listSelectedTicket = new ArrayList<>();

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
        this.createSeat();
        this.reload();
    }

    private CheckBox createCheckBox(int id, String text) {
        CheckBox cb = new CheckBox(text);
        cb.setId(String.valueOf(id));
        Font f = new Font("Courier New", 18);
        cb.setFont(f);
        Insets i = new Insets(10, 0, 10, 110);
        cb.setPadding(i);
        return cb;
    }

    private void createSeat() throws SQLException {
        int tripId = Integer.parseInt(lbID.getText());
        List<Ticket> listTicket = TicketServices.getTicketsByTripID(tripId);
        GridPane gridPane = new GridPane();
        gridPane.setMaxHeight(600);
        int col = 3;
        int row = listTicket.size() / col + 1;
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                int index = row * i + j;
                if (index < listTicket.size()) {
                    Ticket ticket = listTicket.get(index);
                    CheckBox chb = this.createCheckBox(ticket.getId(), ticket.getChair());
                    if (ticket.getStatus().equalsIgnoreCase("Empty")) {
                        chb.setDisable(false);
                    } else {
                        chb.setDisable(true);
                    }

                    chb.selectedProperty()
                            .addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                                try {
                                    Ticket selectedTicket = TicketServices.getTicketById(Integer.parseInt(chb.getId()));
                                    if (newValue) {
                                        listSelectedTicket.add(selectedTicket);
                                    } else {
                                        listSelectedTicket.remove(selectedTicket);
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(Booking_detailController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });

                    gridPane.add(chb, i, j);
                }
            }
        }
        root.add(gridPane, 0, 2);
    }

    public void btnOK_Click(ActionEvent e) {
        String name = this.txtName.getText();
        String phone = this.txtPhone.getText();

        if (name.isEmpty()) {
            MessageBox.getBox("Error", "Cần phải nhập tên khách hàng!", Alert.AlertType.ERROR).show();
        } else {
            if (CheckData.isInteger(phone) && phone.length() == 9 || phone.length() == 10) {
                if (!listSelectedTicket.isEmpty()) {
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setContentText("Bạn có chắc chắn đặt?");
                    confirm.showAndWait().ifPresent((var res) -> {
                        if (res == ButtonType.OK) {
                            try {
                                Customer cus = new Customer(CustomerServices.getLastCustomerID() + 1, name, phone);
                                if (TicketServices.updateTicket(listSelectedTicket, cus)) {
                                    MessageBox.getBox("Information", "Đặt thành công!", Alert.AlertType.INFORMATION).show();
                                    btnCancle_Click(e);
                                } else {
                                    MessageBox.getBox("Error", 
                                            "Có thể ghế đã được thu hồi hoặc được đặt bởi người khác!", 
                                            Alert.AlertType.ERROR).show();
                                }
                            } catch (SQLException | IOException ex) {
                                Logger.getLogger(Booking_detailController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                } else {
                    MessageBox.getBox("Error", "Vui lòng chọn ít nhất 1 ghế!", Alert.AlertType.ERROR).show();
                }
            } else {
                MessageBox.getBox("Error", "Số điện thoại không hợp lệ!", Alert.AlertType.ERROR).show();
            }
        }
    }

    public void btnCancle_Click(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class
                .getResource("booking.fxml"));
        Parent booking = fxmlLoader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(booking));
    }
    
    public void reload() {
        Timer timer = new Timer("Reload");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    createSeat();
                } catch (SQLException ex) {
                    Logger.getLogger(Booking_detailController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        timer.schedule(task, 60*5000L);
    }
}
