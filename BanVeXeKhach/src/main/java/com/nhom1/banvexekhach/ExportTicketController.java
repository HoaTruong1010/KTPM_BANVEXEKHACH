/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.banvexekhach;

import com.google.zxing.WriterException;
import com.nhom1.pojo.Car;
import com.nhom1.pojo.Customer;
import com.nhom1.pojo.Route;
import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.Trip;
import static com.nhom1.pojo.Trip.formatDate;
import com.nhom1.pojo.User;
import com.nhom1.services.CarServices;
import com.nhom1.services.CustomerServices;
import com.nhom1.services.RouteServices;
import com.nhom1.services.TripServices;
import com.nhom1.utils.CreateQR;
import static com.nhom1.utils.CreateQR.generateQRCodeImage;
import com.nhom1.utils.ExportPDF;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author fptshop.com.vn
 */
public class ExportTicketController implements Initializable {

    @FXML
    private Label lbStart;
    @FXML
    private Label lbEnd;
    @FXML
    private Label lbFrom;
    @FXML
    private Label lbTo;
    @FXML
    private Label lbEnter;
    @FXML
    private Label lbBusNumber;
    @FXML
    private ImageView imgvQR;
    @FXML
    private Label lbSeat;
    @FXML
    private Label lbCusName;
    @FXML
    private Label lbUserName;
    @FXML
    private Label lbPrintDate;
    @FXML
    private Label lbTicketID;
    @FXML
    private Label lbStartDate;
    @FXML
    private Label lbPrice;
    @FXML
    private Label lbCusPhone;
    private User currentUser;
    private String chair = "";

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

    public void setInfoInTicket(Ticket t, String name, String phone, String s) throws SQLException, WriterException, IOException {
        chair += t.getChair() + s;
        Trip tr = TripServices.getTripById(t.getTrip_id());
        Route r = RouteServices.getRouteById(tr.getRoute_id());
        Car c = CarServices.getCarById(tr.getCar_id());
        Customer cus = CustomerServices.getCustomer(name, phone);
        LocalDateTime date = LocalDateTime.now();
        this.lbSeat.setText(chair);
        this.lbTicketID.setText(Integer.toString(t.getId()));
        this.lbBusNumber.setText(c.getLicensePlate());
        this.lbCusName.setText(cus.getName());
        this.lbCusPhone.setText(cus.getPhone());
        this.lbEnd.setText(r.getEnd());
        this.lbEnter.setText(r.getStart());
        this.lbStart.setText(r.getStart());
        this.lbFrom.setText(r.getStart());
        this.lbTo.setText(r.getEnd());
        this.lbPrice.setText(Double.toString(tr.getPrice()) + "00");
        this.lbPrintDate.setText(date.format(formatDate));
        this.lbStartDate.setText(tr.getDeparting_at());
        this.imgvQR.setImage(generateQRCodeImage(Integer.toString(t.getId())));
        this.imgvQR.setFitHeight(100);
        this.imgvQR.setFitWidth(100);
        InputStream inputStream = getClass().getResourceAsStream("ticket_export.fxml");
        try {
            ExportPDF.convertFXMLToPDF(inputStream, "D:\\ticket.pdf");

        } catch (IOException ex) {
            Logger.getLogger(ExportTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void btnCancle_Click(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class
                .getResource("sale_ticket.fxml"));
        Parent booking = fxmlLoader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        SaleTicketController bc = fxmlLoader.getController();
        bc.setCurrentUser(currentUser);
        stage.setScene(new Scene(booking));
    }
}
