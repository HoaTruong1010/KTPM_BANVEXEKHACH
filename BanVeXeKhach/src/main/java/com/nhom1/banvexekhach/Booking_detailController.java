/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.Customer;
import com.nhom1.pojo.Route;
import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.Trip;
import com.nhom1.pojo.User;
import com.nhom1.services.CustomerServices;
import com.nhom1.services.RouteServices;
import com.nhom1.services.TicketServices;
import com.nhom1.utils.CheckData;
import com.nhom1.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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

    List<Ticket> listSelectedTicket = new ArrayList<>();
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
    @FXML
    private Label lbCurrentUsername;

    private User currentUser;
    Thread bookingDetailThread;
    boolean isRunning = true;

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

    /**
     * @return the lbID
     */
    public Label getLbID() {
        return lbID;
    }

    /**
     * @param lbID the lbID to set
     */
    public void setLbID(Label lbID) {
        this.lbID = lbID;
    }

    /**
     * @return the lbDeparting
     */
    public Label getLbDeparting() {
        return lbDeparting;
    }

    /**
     * @param lbDeparting the lbDeparting to set
     */
    public void setLbDeparting(Label lbDeparting) {
        this.lbDeparting = lbDeparting;
    }

    @FXML
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookingDetailThread = new Thread(this::reload);
        bookingDetailThread.start();
    }

    public void setTripDetail(Trip trip) throws SQLException {
        getLbID().setText(String.valueOf(trip.getId()));
        getLbDeparting().setText(trip.getDeparting_at());
        lbArriving.setText(trip.getArriving_at());
        lbPrice.setText(String.valueOf(trip.getPrice()));
        Route r = RouteServices.getRouteById(trip.getRoute_id());
        lbRoute.setText(String.format("%s - %s", r.getStart(), r.getEnd()));
        this.createSeat();
    }

    public CheckBox createCheckBox(int id, String text) {
        CheckBox cb = new CheckBox(text);
        cb.setId(String.valueOf(id));
        Font f = new Font("Courier New", 18);
        cb.setFont(f);
        Insets i = new Insets(10, 0, 10, 110);
        cb.setPadding(i);
        return cb;
    }

    public void createSeat() throws SQLException {
        int tripId = Integer.parseInt(getLbID().getText());
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
                                    btnCancle_Click();
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

    public void btnCancle_Click() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class
                .getResource("booking.fxml"));
        Parent booking = fxmlLoader.load();

        Stage stage = (Stage) getLbID().getScene().getWindow();
        BookingController bc = fxmlLoader.getController();
        bc.setCurrentUser(currentUser);
        stage.setScene(new Scene(booking));

    }

    public void reload() {
        while (isRunning) {
            Platform.runLater(() -> {
                try {
                    if (getLbID() != null) {
                        LocalDateTime departing = LocalDateTime.parse(getLbDeparting().getText(), Trip.formatDate);
                        Date now = new Date();
                        long getTime = departing.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                        long getDiff = getTime - now.getTime();
                        if (getDiff > 3600000) {
                            createSeat();
                        } else {
                            isRunning = false;
                            Alert confirm = new Alert(Alert.AlertType.ERROR);
                            confirm.setContentText("Chuyến đi đã không còn cho phép đặt vé!");
                            confirm.showAndWait();
                            try {
                                this.btnCancle_Click();
                            } catch (IOException ex) {
                                Logger.getLogger(Booking_detailController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Booking_detailController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            try {
                if (isRunning) {
                    Thread.sleep(5000);
                } else {
                    Thread.sleep(60000);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Booking_detailController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
