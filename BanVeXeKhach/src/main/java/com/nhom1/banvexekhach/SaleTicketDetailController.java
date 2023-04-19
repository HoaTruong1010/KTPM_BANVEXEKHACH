/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.banvexekhach;

//import com.itextpdf.text.DocumentException;
import com.google.zxing.WriterException;
import com.nhom1.pojo.Customer;
import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.Trip;
import com.nhom1.pojo.User;
import com.nhom1.services.CustomerServices;
import com.nhom1.services.TicketServices;
import com.nhom1.utils.CheckData;
import com.nhom1.utils.ExportPDF;
import com.nhom1.utils.MessageBox;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    boolean isContinued = true;
    boolean isResetTicket = true;
    int selectedCusID = 0;

    /**
     * @return the lbID
     */
    @Override
    public Label getLbID() {
        return lbID;
    }

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
        String name = this.txtName.getText().trim();
        String phone = this.txtPhone.getText().trim();
        TicketServices tks = new TicketServices();
        if (name.isEmpty() || !CheckData.isValidName(name)) {
            MessageBox.getBox("Error", "Tên khách hàng không hợp lệ!", Alert.AlertType.ERROR).show();
        } else {
            if (CheckData.isInteger(phone) && phone.length() == 10) {
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
                                    cus = new Customer(CustomerServices.getLastCustomerID() + 1, name.trim(), phone.trim());
                                } else {
                                    cus = CustomerServices.getCustomer(name, phone);
                                }
                                if (tks.saleTicket(listSelectedTicket, cus, this.getCurrentUser())) {
                                    MessageBox.getBox("Information", "Xuất thành công!", Alert.AlertType.INFORMATION).show();
                                    try {
                                        moveToExport(e, listSelectedTicket);
                                    } catch (WriterException ex) {
                                        Logger.getLogger(SaleTicketDetailController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else {
                                    MessageBox.getBox("Error",
                                            "Có thể ghế đã được thu hồi hoặc được xuất bởi người khác!",
                                            Alert.AlertType.ERROR).show();
                                    createSeat();
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
    public void btnCancle_Click() throws IOException {
        isContinued = false;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class
                .getResource("sale_ticket.fxml"));
        Parent booking = fxmlLoader.load();

        Stage stage = (Stage) getLbID().getScene().getWindow();
        SaleTicketController bc = fxmlLoader.getController();
        bc.setCurrentUser(currentUser);
        stage.setScene(new Scene(booking));
    }

    public void moveToExport(ActionEvent e, List<Ticket> t) throws IOException, SQLException, WriterException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class
                .getResource("ticket_export.fxml"));
        Parent booking = fxmlLoader.load();

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        ExportTicketController bc = fxmlLoader.getController();
        bc.setCurrentUser(currentUser);

        int j = 0;
        for (Ticket i : t) {
            j++;
            String s = " - ";
            if (j == t.size()) {
                s = "";
            }
            bc.setInfoInTicket(i, this.txtName.getText().trim(), this.txtPhone.getText().trim(), s);
        }
        stage.setScene(new Scene(booking));
    }

    public CheckBox createCheckBox(int id, String text, String status) {
        CheckBox cb = new CheckBox(text + ", " + status);
        cb.setId(String.valueOf(id));
        Font f = new Font("Courier New", 15);
        cb.setFont(f);
        Insets i = new Insets(10, 0, 10, 90);
        cb.setPadding(i);
        return cb;
    }

    @Override
    public void createSeat() throws SQLException {
        listSelectedTicket.clear();
        int tripId = Integer.parseInt(getLbID().getText());
        List<Ticket> listTicket = TicketServices.getTicketsByTripID(tripId);
        GridPane gridPane = new GridPane();
        gridPane.setMaxHeight(600);
        int col = 3;
        int row = listTicket.size() / col;
        if (listTicket.size() % col != 0) {
            row += 1;
        }
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                int index = row * i + j;
                if (index < listTicket.size()) {
                    Ticket ticket = listTicket.get(index);
                    CheckBox chb = createCheckBox(ticket.getId(), ticket.getChair(), ticket.getStatus());
                    if (!ticket.getStatus().equalsIgnoreCase("Sold")) {
                        chb.setDisable(false);
                        chb.setSelected(false);
                    } else {
                        chb.setDisable(true);
                        chb.setSelected(true);
                    }

                    chb.selectedProperty()
                            .addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                                try {
                                    Ticket selectedTicket = TicketServices.getTicketById(Integer.parseInt(chb.getId()));
                                    CustomerServices cs = new CustomerServices();
                                    Customer cus = cs.getCustomerByID(selectedTicket.getCustomer_id());
                                    if (newValue) {
                                        listSelectedTicket.add(selectedTicket);
                                        if (this.txtName.getText() != "" && this.txtPhone.getText() != "") {
                                            if (cus != null) {
                                                if (!cus.getName().equals(this.txtName.getText()) && !cus.getPhone().equals(this.txtPhone.getText())) {
                                                    MessageBox.getBox("Warning", "Khách đặt không trùng khớp!", Alert.AlertType.WARNING).show();
                                                    listSelectedTicket.remove(selectedTicket);
                                                    chb.setSelected(false);
                                                    cus.setName(this.txtName.getText());
                                                    cus.setPhone(this.txtPhone.getText());
                                                }
                                            }
                                        }

                                        if (cus != null) {
                                            this.txtName.setText(cus.getName());
                                            this.txtPhone.setText(cus.getPhone());
                                        }
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

    @Override
    public void reload() {
        while (isContinued) {
            Platform.runLater(() -> {
                if (getLbID() != null) {
                    if (!CheckData.isChoosing(getLbDeparting().getText(), 300000)) {
                        isContinued = false;
                        Alert confirm = new Alert(Alert.AlertType.ERROR);
                        confirm.setContentText("Chuyến đi đã không còn cho phép bán vé!");
                        confirm.showAndWait();
                        try {
                            this.btnCancle_Click();
                        } catch (IOException ex) {
                            Logger.getLogger(SaleTicketDetailController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

            try {
                if (isContinued) {
                    Thread.sleep(5000);
                } else {
                    Thread.sleep(60 * 60000);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Booking_detailController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
