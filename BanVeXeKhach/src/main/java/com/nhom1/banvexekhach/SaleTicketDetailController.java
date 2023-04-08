/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.nhom1.pojo.Customer;
import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.User;
import com.nhom1.services.CustomerServices;
import com.nhom1.services.TicketServices;
import com.nhom1.utils.CheckData;
import com.nhom1.utils.MessageBox;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author fptshop.com.vn
 */
public class SaleTicketDetailController extends Booking_detailController {

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

    /**
     * @return the currentUser
     */
    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    @Override
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        this.lbCurrentUsername.setText(String.format("%s",
                currentUser.getName()));
    }

    @Override
    public void btnOK_Click(ActionEvent e) {
        String name = this.txtName.getText();
        String phone = this.txtPhone.getText();

        if (name.isEmpty()) {
            MessageBox.getBox("Error", "Cần phải nhập tên khách hàng!", Alert.AlertType.ERROR).show();
        } else {
            if (CheckData.isInteger(phone) && phone.length() == 9 || phone.length() == 10) {
                if (!listSelectedTicket.isEmpty()) {
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setContentText("Bạn có chắc chắn xuất vé?");
                    confirm.showAndWait().ifPresent((var res) -> {
                        if (res == ButtonType.OK) {
                            try {
                                Customer cus;
                                if (!CustomerServices.isExistCustomer(name, phone)) {
                                    if (CheckData.isReservedTicket(listSelectedTicket)) {
                                        MessageBox.getBox("WARNING", "Vé đã được đặt bởi khách hàng khác!", Alert.AlertType.WARNING).show();
                                        return;
                                    }
                                    cus = new Customer(CustomerServices.getLastCustomerID() + 1, name, phone);
                                } else {
                                    cus = CustomerServices.getCustomer(name, phone);
                                }
                                if (TicketServices.saleTicket(listSelectedTicket, cus, this.getCurrentUser())) {
                                    MessageBox.getBox("Information", "Xuất thành công!", Alert.AlertType.INFORMATION).show();
                                    btnCancle_Click(e);
                                } else {
                                    MessageBox.getBox("Error",
                                            "Có thể ghế đã được thu hồi hoặc được xuất bởi người khác!",
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

    @Override
    public void btnCancle_Click(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class
                .getResource("sale_ticket.fxml"));
        Parent booking = fxmlLoader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        SaleTicketController bc = fxmlLoader.getController();
        bc.setCurrentUser(currentUser);
        stage.setScene(new Scene(booking));
    }

    @Override
    public void createSeat() throws SQLException {
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
                    CheckBox chb = super.createCheckBox(ticket.getId(), ticket.getChair());
                    if (!ticket.getStatus().equalsIgnoreCase("Sold")) {
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
}
